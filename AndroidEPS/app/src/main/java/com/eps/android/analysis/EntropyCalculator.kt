package com.eps.android.analysis

import java.io.File
import javax.inject.Inject
import kotlin.math.log2

class EntropyCalculator @Inject constructor() {
    fun calculateEntropy(file: File): Double {
        return try {
            val bytes = file.readBytes()
            if (bytes.isEmpty()) return 0.0
            
            val frequencies = IntArray(256)
            for (byte in bytes) {
                frequencies[byte.toInt() and 0xFF]++
            }
            
            var entropy = 0.0
            val size = bytes.size.toDouble()
            for (count in frequencies) {
                if (count > 0) {
                    val p = count / size
                    entropy -= p * log2(p)
                }
            }
            entropy
        } catch (e: Exception) {
            0.0
        }
    }
}
