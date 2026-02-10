package com.eps.android.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import androidx.core.app.NotificationCompat
import com.eps.android.MainActivity
import com.eps.android.R
import com.eps.android.data.ThreatEvent
import com.eps.android.data.ThreatEventDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@AndroidEntryPoint
class AntiPhishingVpnService : VpnService() {

    @Inject lateinit var threatEventDao: ThreatEventDao
    @Inject lateinit var blacklistDao: com.eps.android.data.BlacklistDao

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var vpnInterface: ParcelFileDescriptor? = null
    private val isRunning = AtomicBoolean(false)
    private var vpnThread: Thread? = null
    
    // Dynamic Blacklist Cache
    private val cachedBlacklist = java.util.concurrent.ConcurrentHashMap.newKeySet<String>()

    // DNS Constants
    private val DNS_SERVER_IP = "8.8.8.8" // Upstream (Allowed)
    private val DNS_SERVER_PORT = 53
    private val VPN_ADDRESS = "10.0.0.2"

    private val staticBlacklist = setOf(
        "evil-phish.net", "fake-google-login.com", "malicious-site.tk",
        "telegram-official-verify.com", "my-account-secure.org",
        "pornhub.com", "xhamster.com", "xnxx.com", "beeg.com", "redtube.com", 
        "bet365.com", "1xbet.com", "mostbet.com", "parimatch.com", "melbet.com",
        "olx-uz.xyz", "uzcard-auth.top", "click-verify.cc", "payme-login.uz",
        "official-click.uz", "id-uzcard.com", "lucky-sms.net"
    )

    private val maliciousPatterns = listOf(
        Regex(".*verify.*", RegexOption.IGNORE_CASE),
        Regex(".*login.*", RegexOption.IGNORE_CASE),
        Regex(".*account.*", RegexOption.IGNORE_CASE),
        Regex(".*kabinet.*", RegexOption.IGNORE_CASE),
        Regex(".*payme-.*", RegexOption.IGNORE_CASE),
        Regex(".*uzcard-.*", RegexOption.IGNORE_CASE),
        Regex(".*click-.*", RegexOption.IGNORE_CASE),
        Regex(".*auth-.*", RegexOption.IGNORE_CASE),
        Regex(".*secure-.*", RegexOption.IGNORE_CASE),
        Regex(".*-promo.*", RegexOption.IGNORE_CASE),
        Regex(".*bonus-.*", RegexOption.IGNORE_CASE),
        Regex(".*localhost.*", RegexOption.IGNORE_CASE)
    )

    companion object {
        const val CHANNEL_ID = "hackdefender_vpn_channel"
        const val NOTIFICATION_ID = 101
        const val ACTION_STOP = "com.eps.android.STOP_VPN"
        
        // Public status for UI synchronization
        val isServiceRunning = AtomicBoolean(false)
    }

    private fun loadBlacklistCache() {
        serviceScope.launch {
            try {
                cachedBlacklist.addAll(staticBlacklist)
                val dbDomains = blacklistDao.getAllDomains()
                dbDomains.forEach { cachedBlacklist.add(it.domain) }
                Timber.i("VPN Blacklist loaded: ${cachedBlacklist.size} domains")
            } catch (e: Exception) {
                Timber.e(e, "Error loading blacklist")
            }
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_STOP) {
            stopVpn()
            return START_NOT_STICKY
        }

        try {
            if (!isRunning.get()) {
                startForeground(NOTIFICATION_ID, createNotification())
                loadBlacklistCache()
                setupVpn()
            }
        } catch (e: Exception) {
            Timber.e(e, "Fatal Error starting VPN Service")
            stopSelf()
        }
        return START_STICKY
    }

    private fun setupVpn() {
        try {
            if (vpnInterface != null) {
                vpnInterface?.close()
            }

            val builder = Builder()
                .setSession("Cyber Brother DNS Shield")
                .addAddress(VPN_ADDRESS, 32)
                // Use the VPN itself as DNS server for the system
                .addDnsServer(VPN_ADDRESS) 
                .addRoute("0.0.0.0", 0) // Intercept all for DNS tracking
                .setBlocking(true)
                .setMtu(1500)

            vpnInterface = builder.establish()

            if (vpnInterface != null) {
                isRunning.set(true)
                isServiceRunning.set(true)
                vpnThread = Thread { runVpnLoop() }
                vpnThread?.start()
                Timber.i("Cyber Shield Active")
            }
        } catch (e: Exception) {
            Timber.e(e, "VPN Setup Error")
            stopSelf()
        }
    }


    private fun runVpnLoop() {
        val inputStream = FileInputStream(vpnInterface!!.fileDescriptor)
        val outputStream = FileOutputStream(vpnInterface!!.fileDescriptor)
        
        // Prepare a buffer for packets
        val packet = ByteBuffer.allocate(32767)
        
        // Protected socket for upstream queries
        val upstreamSocket = DatagramSocket()
        protect(upstreamSocket)

        while (isRunning.get() && vpnInterface != null) {
            try {
                // Read packet from VPN interface
                val length = inputStream.read(packet.array())
                if (length > 0) {
                    processPacket(packet, length, outputStream, upstreamSocket)
                }
                packet.clear()
            } catch (e: Exception) {
                if (isRunning.get()) {
                    Timber.e(e, "Error in VPN loop")
                }
            }
        }
        
        upstreamSocket.close()
    }

    private fun processPacket(
        packet: ByteBuffer, 
        length: Int, 
        vpnOutput: FileOutputStream,
        upstreamSocket: DatagramSocket
    ) {
        val buffer = packet.array()
        
        // Basic IPv4 Header parsing
        val combinedVersionHeader = buffer[0].toInt()
        val version = combinedVersionHeader shr 4
        if (version != 4) return // Only support IPv4 for now
        
        val headerLength = (combinedVersionHeader and 0x0F) * 4
        val protocol = buffer[9].toInt()
        
        // Check if UDP (Protocol 17)
        if (protocol == 17) {
            val srcPort = ((buffer[headerLength].toInt() and 0xFF) shl 8) + (buffer[headerLength + 1].toInt() and 0xFF)
            val dstPort = ((buffer[headerLength + 2].toInt() and 0xFF) shl 8) + (buffer[headerLength + 3].toInt() and 0xFF)
            
            // Intercept DNS (Port 53)
            if (dstPort == 53) {
                handleDnsRequest(buffer, length, headerLength, vpnOutput, upstreamSocket)
            } else {
                // For now, just drop non-DNS UDP packets or implement NAT (NAT is complex, usually dropping is safer for a 'DNS Firewall' mode effectively blocking other UDP)
            }
        }
    }

    private fun handleDnsRequest(
        buffer: ByteArray, 
        length: Int, 
        ipHeaderLen: Int, 
        vpnOutput: FileOutputStream,
        upstreamSocket: DatagramSocket
    ) {
        // UDP Header is 8 bytes
        val udpHeaderLen = 8
        val dnsOffset = ipHeaderLen + udpHeaderLen
        
        try {
            // Extract original IP header info for NAT
            val srcIp = ByteArray(4)
            val dstIp = ByteArray(4)
            System.arraycopy(buffer, 12, srcIp, 0, 4) // Source IP
            System.arraycopy(buffer, 16, dstIp, 0, 4) // Destination IP
            
            // Extract UDP ports
            val srcPort = ((buffer[ipHeaderLen].toInt() and 0xFF) shl 8) + (buffer[ipHeaderLen + 1].toInt() and 0xFF)
            val dstPort = ((buffer[ipHeaderLen + 2].toInt() and 0xFF) shl 8) + (buffer[ipHeaderLen + 3].toInt() and 0xFF)
            
            // Extract Domain Name from DNS Query
            val queryDomain = parseDnsQuery(buffer, dnsOffset)
            
            if (queryDomain != null) {
                // Check Blacklist
                if (isDomainMalicious(queryDomain)) {
                    Timber.w("BLOCKING DNS: $queryDomain")
                    handleThreat(queryDomain, "Phishing/Malicious Domain Detected")
                    // Send NXDOMAIN response (domain not found)
                    sendNxDomainResponse(buffer, dnsOffset, srcIp, dstIp, srcPort, dstPort, vpnOutput)
                    return 
                }
                
                // Allow: Forward to real DNS (8.8.8.8)
                val dnsPayloadLen = length - dnsOffset
                val dnsPayload = ByteArray(dnsPayloadLen)
                System.arraycopy(buffer, dnsOffset, dnsPayload, 0, dnsPayloadLen)
                
                val outPacket = DatagramPacket(dnsPayload, dnsPayloadLen, InetAddress.getByName(DNS_SERVER_IP), DNS_SERVER_PORT)
                upstreamSocket.send(outPacket)
                
                // Read Response from Google DNS
                val respBuffer = ByteArray(4096)
                val inPacket = DatagramPacket(respBuffer, respBuffer.size)
                upstreamSocket.soTimeout = 5000 // 5 second timeout
                upstreamSocket.receive(inPacket)
                
                // Construct IP + UDP + DNS response packet
                val dnsResponseLen = inPacket.length
                val responsePacket = buildResponsePacket(
                    srcIp, dstIp, srcPort, dstPort,
                    respBuffer, dnsResponseLen
                )
                
                // Write back to VPN interface
                vpnOutput.write(responsePacket)
                vpnOutput.flush()
                
                Timber.d("DNS Response sent for: $queryDomain")
            }
        } catch (e: java.net.SocketTimeoutException) {
            Timber.e("DNS timeout")
        } catch (e: Exception) {
            Timber.e(e, "Error handling DNS")
        }
    }
    
    private fun sendNxDomainResponse(
        originalBuffer: ByteArray,
        dnsOffset: Int,
        srcIp: ByteArray,
        dstIp: ByteArray,
        srcPort: Int,
        dstPort: Int,
        vpnOutput: FileOutputStream
    ) {
        try {
            // Create minimal NXDOMAIN response (DNS error code 3)
            val dnsResponse = ByteArray(12) // DNS header only
            // Copy transaction ID from original query
            System.arraycopy(originalBuffer, dnsOffset, dnsResponse, 0, 2)
            // Set flags: Response (0x8000) + NXDOMAIN (0x0003)
            dnsResponse[2] = 0x81.toByte()
            dnsResponse[3] = 0x83.toByte()
            
            val responsePacket = buildResponsePacket(srcIp, dstIp, srcPort, dstPort, dnsResponse, dnsResponse.size)
            vpnOutput.write(responsePacket)
            vpnOutput.flush()
        } catch (e: Exception) {
            Timber.e(e, "Error sending NXDOMAIN")
        }
    }
    
    private fun buildResponsePacket(
        srcIp: ByteArray,
        dstIp: ByteArray,
        srcPort: Int,
        dstPort: Int,
        dnsPayload: ByteArray,
        dnsLen: Int
    ): ByteArray {
        val ipHeaderLen = 20
        val udpHeaderLen = 8
        val totalLen = ipHeaderLen + udpHeaderLen + dnsLen
        
        val packet = ByteArray(totalLen)
        
        // === IP Header ===
        packet[0] = 0x45 // Version 4, Header length 5 (20 bytes)
        packet[1] = 0x00 // DSCP/ECN
        // Total length
        packet[2] = (totalLen shr 8).toByte()
        packet[3] = (totalLen and 0xFF).toByte()
        // Identification (can be random)
        packet[4] = 0x00
        packet[5] = 0x00
        // Flags and Fragment offset
        packet[6] = 0x40 // Don't fragment
        packet[7] = 0x00
        // TTL
        packet[8] = 64
        // Protocol (UDP = 17)
        packet[9] = 17
        // Checksum (will calculate)
        packet[10] = 0x00
        packet[11] = 0x00
        // Source IP (swap: original dst becomes src)
        System.arraycopy(dstIp, 0, packet, 12, 4)
        // Destination IP (swap: original src becomes dst)
        System.arraycopy(srcIp, 0, packet, 16, 4)
        
        // Calculate IP checksum
        val ipChecksum = calculateChecksum(packet, 0, ipHeaderLen)
        packet[10] = (ipChecksum shr 8).toByte()
        packet[11] = (ipChecksum and 0xFF).toByte()
        
        // === UDP Header ===
        val udpStart = ipHeaderLen
        // Source port (swap: original dst becomes src)
        packet[udpStart] = (dstPort shr 8).toByte()
        packet[udpStart + 1] = (dstPort and 0xFF).toByte()
        // Destination port (swap: original src becomes dst)
        packet[udpStart + 2] = (srcPort shr 8).toByte()
        packet[udpStart + 3] = (srcPort and 0xFF).toByte()
        // UDP length
        val udpLen = udpHeaderLen + dnsLen
        packet[udpStart + 4] = (udpLen shr 8).toByte()
        packet[udpStart + 5] = (udpLen and 0xFF).toByte()
        // UDP checksum (optional, set to 0)
        packet[udpStart + 6] = 0x00
        packet[udpStart + 7] = 0x00
        
        // === DNS Payload ===
        System.arraycopy(dnsPayload, 0, packet, ipHeaderLen + udpHeaderLen, dnsLen)
        
        return packet
    }
    
    private fun calculateChecksum(data: ByteArray, offset: Int, length: Int): Int {
        var sum = 0L
        var i = offset
        while (i < offset + length - 1) {
            sum += ((data[i].toInt() and 0xFF) shl 8) or (data[i + 1].toInt() and 0xFF)
            i += 2
        }
        if (i < offset + length) {
            sum += (data[i].toInt() and 0xFF) shl 8
        }
        while (sum shr 16 != 0L) {
            sum = (sum and 0xFFFF) + (sum shr 16)
        }
        return (sum.inv() and 0xFFFF).toInt()
    }
    
    // Simple parser for extracting domain from DNS question
    private fun parseDnsQuery(buffer: ByteArray, offset: Int): String? {
        // DNS Header is 12 bytes
        var pos = offset + 12 
        val sb = StringBuilder()
        
        try {
            var len = buffer[pos].toInt()
            while (len > 0) {
                if (sb.isNotEmpty()) sb.append(".")
                
                for (i in 0 until len) {
                    sb.append(buffer[pos + 1 + i].toChar())
                }
                pos += len + 1
                len = buffer[pos].toInt()
                
                // Jump pointer check (compression) - ignored for simplicity in Query (queries usually aren't compressed)
                if (len < 0) return sb.toString() // End or error
            }
            return sb.toString()
        } catch (e: Exception) {
            return null
        }
    }

    private fun isDomainMalicious(domain: String): Boolean {
        // Check cached blacklist (includes DB + static)
        if (cachedBlacklist.contains(domain)) return true
        
        // Check regex patterns
        return maliciousPatterns.any { it.matches(domain) }
    }

    private fun handleThreat(domain: String, reason: String) {
        serviceScope.launch {
            threatEventDao.insertEvent(ThreatEvent(
                type = "NETWORK_SHIELD",
                severity = "CRITICAL",
                source = domain,
                details = "Bloklandi: $domain ($reason)"
            ))
        }
        
        // Show urgent notification
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_notify_error)
            .setContentTitle("HACKDEFENDER: XAVF ANIQLANDI!")
            .setContentText("Sayt bloklandi: $domain")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(domain.hashCode(), notification)
    }

    private fun createNotification(): Notification {
        val channel = NotificationChannel(CHANNEL_ID, "HACKDEFENDER VPN", NotificationManager.IMPORTANCE_LOW)
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        val stopIntent = Intent(this, AntiPhishingVpnService::class.java).apply { action = ACTION_STOP }
        val pendingStop = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("HACKDEFENDER Himoyasi Faol")
            .setContentText("Internet trafigi filtrlanmoqda...")
            .setSmallIcon(R.drawable.ic_shield)
            .addAction(R.drawable.ic_launcher, "To'xtatish", pendingStop)
            .build()
    }

    private fun stopVpn() {
        isRunning.set(false)
        isServiceRunning.set(false)
        vpnThread?.interrupt()
        vpnInterface?.close()
        vpnInterface = null
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        stopVpn()
        super.onDestroy()
    }
}
