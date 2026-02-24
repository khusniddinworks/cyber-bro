import java.util.Base64

fun main() {
    val original = "bcb1b9d1a3934b816ebd740a4b572b14cf3fd6f842a776d4d3cd3e9a93d5bb22"
    val key = 0x42
    val result = ByteArray(original.length)
    for (i in original.indices) {
        result[i] = (original[i].toInt() xor key).toByte()
    }
    val encrypted = Base64.getEncoder().encodeToString(result)
    println(encrypted)
}
