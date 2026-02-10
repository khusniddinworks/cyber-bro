package com.eps.android.core;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u0000 P2\u00020\u0001:\u0001PB\u0005\u00a2\u0006\u0002\u0010\u0002J8\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020&2\u0006\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020\u0006H\u0002J \u0010-\u001a\u00020\u00062\u0006\u0010.\u001a\u00020&2\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u0006H\u0002J\b\u00101\u001a\u000202H\u0002J0\u00103\u001a\u0002042\u0006\u00105\u001a\u00020&2\u0006\u00100\u001a\u00020\u00062\u0006\u00106\u001a\u00020\u00062\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0002J\u0018\u0010;\u001a\u0002042\u0006\u0010<\u001a\u00020\u00042\u0006\u0010=\u001a\u00020\u0004H\u0002J\u0010\u0010>\u001a\u00020\u00112\u0006\u0010<\u001a\u00020\u0004H\u0002J\b\u0010?\u001a\u000204H\u0002J\b\u0010@\u001a\u000204H\u0016J\"\u0010A\u001a\u00020\u00062\b\u0010B\u001a\u0004\u0018\u00010C2\u0006\u0010D\u001a\u00020\u00062\u0006\u0010E\u001a\u00020\u0006H\u0016J\u001a\u0010F\u001a\u0004\u0018\u00010\u00042\u0006\u00105\u001a\u00020&2\u0006\u0010/\u001a\u00020\u0006H\u0002J(\u0010G\u001a\u0002042\u0006\u0010H\u001a\u00020I2\u0006\u00100\u001a\u00020\u00062\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0002J\b\u0010J\u001a\u000204H\u0002J@\u0010K\u001a\u0002042\u0006\u0010L\u001a\u00020&2\u0006\u0010M\u001a\u00020\u00062\u0006\u0010\'\u001a\u00020&2\u0006\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00062\u0006\u00107\u001a\u000208H\u0002J\b\u0010N\u001a\u000204H\u0002J\b\u0010O\u001a\u000204H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rRN\u0010\u000e\u001aB\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00110\u0011 \u0010* \u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00110\u0011\u0018\u00010\u000f0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001b\u001a\u00020\u001c8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006Q"}, d2 = {"Lcom/eps/android/core/AntiPhishingVpnService;", "Landroid/net/VpnService;", "()V", "DNS_SERVER_IP", "", "DNS_SERVER_PORT", "", "VPN_ADDRESS", "blacklistDao", "Lcom/eps/android/data/BlacklistDao;", "getBlacklistDao", "()Lcom/eps/android/data/BlacklistDao;", "setBlacklistDao", "(Lcom/eps/android/data/BlacklistDao;)V", "cachedBlacklist", "Ljava/util/concurrent/ConcurrentHashMap$KeySetView;", "kotlin.jvm.PlatformType", "", "isRunning", "Ljava/util/concurrent/atomic/AtomicBoolean;", "maliciousPatterns", "", "Lkotlin/text/Regex;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "staticBlacklist", "", "threatEventDao", "Lcom/eps/android/data/ThreatEventDao;", "getThreatEventDao", "()Lcom/eps/android/data/ThreatEventDao;", "setThreatEventDao", "(Lcom/eps/android/data/ThreatEventDao;)V", "vpnInterface", "Landroid/os/ParcelFileDescriptor;", "vpnThread", "Ljava/lang/Thread;", "buildResponsePacket", "", "srcIp", "dstIp", "srcPort", "dstPort", "dnsPayload", "dnsLen", "calculateChecksum", "data", "offset", "length", "createNotification", "Landroid/app/Notification;", "handleDnsRequest", "", "buffer", "ipHeaderLen", "vpnOutput", "Ljava/io/FileOutputStream;", "upstreamSocket", "Ljava/net/DatagramSocket;", "handleThreat", "domain", "reason", "isDomainMalicious", "loadBlacklistCache", "onDestroy", "onStartCommand", "intent", "Landroid/content/Intent;", "flags", "startId", "parseDnsQuery", "processPacket", "packet", "Ljava/nio/ByteBuffer;", "runVpnLoop", "sendNxDomainResponse", "originalBuffer", "dnsOffset", "setupVpn", "stopVpn", "Companion", "app_debug"})
public final class AntiPhishingVpnService extends android.net.VpnService {
    @javax.inject.Inject
    public com.eps.android.data.ThreatEventDao threatEventDao;
    @javax.inject.Inject
    public com.eps.android.data.BlacklistDao blacklistDao;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.Nullable
    private android.os.ParcelFileDescriptor vpnInterface;
    @org.jetbrains.annotations.NotNull
    private final java.util.concurrent.atomic.AtomicBoolean isRunning = null;
    @org.jetbrains.annotations.Nullable
    private java.lang.Thread vpnThread;
    private final java.util.concurrent.ConcurrentHashMap.KeySetView<java.lang.String, java.lang.Boolean> cachedBlacklist = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String DNS_SERVER_IP = "8.8.8.8";
    private final int DNS_SERVER_PORT = 53;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String VPN_ADDRESS = "10.0.0.2";
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> staticBlacklist = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<kotlin.text.Regex> maliciousPatterns = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CHANNEL_ID = "hackdefender_vpn_channel";
    public static final int NOTIFICATION_ID = 101;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_STOP = "com.eps.android.STOP_VPN";
    @org.jetbrains.annotations.NotNull
    private static final java.util.concurrent.atomic.AtomicBoolean isServiceRunning = null;
    @org.jetbrains.annotations.NotNull
    public static final com.eps.android.core.AntiPhishingVpnService.Companion Companion = null;
    
    public AntiPhishingVpnService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.ThreatEventDao getThreatEventDao() {
        return null;
    }
    
    public final void setThreatEventDao(@org.jetbrains.annotations.NotNull
    com.eps.android.data.ThreatEventDao p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.BlacklistDao getBlacklistDao() {
        return null;
    }
    
    public final void setBlacklistDao(@org.jetbrains.annotations.NotNull
    com.eps.android.data.BlacklistDao p0) {
    }
    
    private final void loadBlacklistCache() {
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void setupVpn() {
    }
    
    private final void runVpnLoop() {
    }
    
    private final void processPacket(java.nio.ByteBuffer packet, int length, java.io.FileOutputStream vpnOutput, java.net.DatagramSocket upstreamSocket) {
    }
    
    private final void handleDnsRequest(byte[] buffer, int length, int ipHeaderLen, java.io.FileOutputStream vpnOutput, java.net.DatagramSocket upstreamSocket) {
    }
    
    private final void sendNxDomainResponse(byte[] originalBuffer, int dnsOffset, byte[] srcIp, byte[] dstIp, int srcPort, int dstPort, java.io.FileOutputStream vpnOutput) {
    }
    
    private final byte[] buildResponsePacket(byte[] srcIp, byte[] dstIp, int srcPort, int dstPort, byte[] dnsPayload, int dnsLen) {
        return null;
    }
    
    private final int calculateChecksum(byte[] data, int offset, int length) {
        return 0;
    }
    
    private final java.lang.String parseDnsQuery(byte[] buffer, int offset) {
        return null;
    }
    
    private final boolean isDomainMalicious(java.lang.String domain) {
        return false;
    }
    
    private final void handleThreat(java.lang.String domain, java.lang.String reason) {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    private final void stopVpn() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/eps/android/core/AntiPhishingVpnService$Companion;", "", "()V", "ACTION_STOP", "", "CHANNEL_ID", "NOTIFICATION_ID", "", "isServiceRunning", "Ljava/util/concurrent/atomic/AtomicBoolean;", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.concurrent.atomic.AtomicBoolean isServiceRunning() {
            return null;
        }
    }
}