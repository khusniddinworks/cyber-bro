package com.eps.android.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0005R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0015"}, d2 = {"Lcom/eps/android/analysis/VishingPatternMatcher;", "", "()V", "ENGLISH_PATTERNS", "", "", "getENGLISH_PATTERNS", "()Ljava/util/List;", "RUSSIAN_PATTERNS", "getRUSSIAN_PATTERNS", "UZBEK_PATTERNS", "getUZBEK_PATTERNS", "lastDetectionTimestamp", "", "getLastDetectionTimestamp", "()J", "setLastDetectionTimestamp", "(J)V", "calculateVishingScore", "", "text", "app_debug"})
public final class VishingPatternMatcher {
    private static long lastDetectionTimestamp = 0L;
    
    /**
     * Common phishing keywords/phrases in Uzbek, Russian, and English
     * focused on "Vishing" (Voice Phishing).
     */
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> UZBEK_PATTERNS = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> RUSSIAN_PATTERNS = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> ENGLISH_PATTERNS = null;
    @org.jetbrains.annotations.NotNull
    public static final com.eps.android.analysis.VishingPatternMatcher INSTANCE = null;
    
    private VishingPatternMatcher() {
        super();
    }
    
    public final long getLastDetectionTimestamp() {
        return 0L;
    }
    
    public final void setLastDetectionTimestamp(long p0) {
    }
    
    /**
     * Common phishing keywords/phrases in Uzbek, Russian, and English
     * focused on "Vishing" (Voice Phishing).
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getUZBEK_PATTERNS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getRUSSIAN_PATTERNS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getENGLISH_PATTERNS() {
        return null;
    }
    
    /**
     * Checks if a text snippet contains phishing indicators.
     * Returns a score between 0.0 and 1.0
     */
    public final double calculateVishingScore(@org.jetbrains.annotations.NotNull
    java.lang.String text) {
        return 0.0;
    }
}