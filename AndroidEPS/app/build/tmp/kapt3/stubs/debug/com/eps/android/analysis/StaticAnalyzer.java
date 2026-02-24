package com.eps.android.analysis;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0012B1\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0019\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"Lcom/eps/android/analysis/StaticAnalyzer;", "", "context", "Landroid/content/Context;", "hashCalculator", "Lcom/eps/android/analysis/HashCalculator;", "entropyCalculator", "Lcom/eps/android/analysis/EntropyCalculator;", "apkAnalyzer", "Lcom/eps/android/analysis/ApkAnalyzer;", "malwareClassifier", "Lcom/eps/android/analysis/ai/MalwareClassifier;", "(Landroid/content/Context;Lcom/eps/android/analysis/HashCalculator;Lcom/eps/android/analysis/EntropyCalculator;Lcom/eps/android/analysis/ApkAnalyzer;Lcom/eps/android/analysis/ai/MalwareClassifier;)V", "analyzeFile", "Lcom/eps/android/analysis/StaticAnalyzer$FileVerdict;", "file", "Ljava/io/File;", "(Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "FileVerdict", "app_debug"})
public final class StaticAnalyzer {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.HashCalculator hashCalculator = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.EntropyCalculator entropyCalculator = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.ApkAnalyzer apkAnalyzer = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.ai.MalwareClassifier malwareClassifier = null;
    
    @javax.inject.Inject
    public StaticAnalyzer(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.HashCalculator hashCalculator, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.EntropyCalculator entropyCalculator, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.ApkAnalyzer apkAnalyzer, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.ai.MalwareClassifier malwareClassifier) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object analyzeFile(@org.jetbrains.annotations.NotNull
    java.io.File file, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.eps.android.analysis.StaticAnalyzer.FileVerdict> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0019\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\bH\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003J\t\u0010#\u001a\u00020\u000eH\u00c6\u0003J_\u0010$\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u00c6\u0001J\u0013\u0010%\u001a\u00020\b2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020(H\u00d6\u0001J\t\u0010)\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0018R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017\u00a8\u0006*"}, d2 = {"Lcom/eps/android/analysis/StaticAnalyzer$FileVerdict;", "", "hash", "", "entropy", "", "riskLevel", "isSuspicious", "", "reason", "packageName", "detectedPermissions", "", "aiScore", "", "(Ljava/lang/String;DLjava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/util/List;F)V", "getAiScore", "()F", "getDetectedPermissions", "()Ljava/util/List;", "getEntropy", "()D", "getHash", "()Ljava/lang/String;", "()Z", "getPackageName", "getReason", "getRiskLevel", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class FileVerdict {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String hash = null;
        private final double entropy = 0.0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String riskLevel = null;
        private final boolean isSuspicious = false;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String reason = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String packageName = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> detectedPermissions = null;
        private final float aiScore = 0.0F;
        
        public FileVerdict(@org.jetbrains.annotations.NotNull
        java.lang.String hash, double entropy, @org.jetbrains.annotations.NotNull
        java.lang.String riskLevel, boolean isSuspicious, @org.jetbrains.annotations.NotNull
        java.lang.String reason, @org.jetbrains.annotations.NotNull
        java.lang.String packageName, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> detectedPermissions, float aiScore) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getHash() {
            return null;
        }
        
        public final double getEntropy() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getRiskLevel() {
            return null;
        }
        
        public final boolean isSuspicious() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getReason() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPackageName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getDetectedPermissions() {
            return null;
        }
        
        public final float getAiScore() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        public final boolean component4() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> component7() {
            return null;
        }
        
        public final float component8() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.StaticAnalyzer.FileVerdict copy(@org.jetbrains.annotations.NotNull
        java.lang.String hash, double entropy, @org.jetbrains.annotations.NotNull
        java.lang.String riskLevel, boolean isSuspicious, @org.jetbrains.annotations.NotNull
        java.lang.String reason, @org.jetbrains.annotations.NotNull
        java.lang.String packageName, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> detectedPermissions, float aiScore) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}