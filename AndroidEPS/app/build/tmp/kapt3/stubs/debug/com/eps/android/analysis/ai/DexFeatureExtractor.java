package com.eps.android.analysis.ai;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0013B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J \u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u000bH\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0006J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\rH\u0002\u00a8\u0006\u0014"}, d2 = {"Lcom/eps/android/analysis/ai/DexFeatureExtractor;", "", "()V", "checkSuspiciousStrings", "", "file", "Ljava/io/File;", "pattern", "", "containsPattern", "buffer", "", "length", "", "extractFeatures", "Lcom/eps/android/analysis/ai/DexFeatureExtractor$DexFeatures;", "apkFile", "readIntLE", "offset", "DexFeatures", "app_debug"})
public final class DexFeatureExtractor {
    
    @javax.inject.Inject
    public DexFeatureExtractor() {
        super();
    }
    
    /**
     * Extracts features from the primary classes.dex of an APK
     */
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.ai.DexFeatureExtractor.DexFeatures extractFeatures(@org.jetbrains.annotations.NotNull
    java.io.File apkFile) {
        return null;
    }
    
    private final int readIntLE(byte[] buffer, int offset) {
        return 0;
    }
    
    private final boolean checkSuspiciousStrings(java.io.File file, java.lang.String pattern) {
        return false;
    }
    
    private final boolean containsPattern(byte[] buffer, int length, byte[] pattern) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003JE\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u00062\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f\u00a8\u0006\u001e"}, d2 = {"Lcom/eps/android/analysis/ai/DexFeatureExtractor$DexFeatures;", "", "classCount", "", "methodCount", "hasDynamicLoading", "", "hasReflection", "hasInsecureNetwork", "isHighlyObfuscated", "(IIZZZZ)V", "getClassCount", "()I", "getHasDynamicLoading", "()Z", "getHasInsecureNetwork", "getHasReflection", "getMethodCount", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
    public static final class DexFeatures {
        private final int classCount = 0;
        private final int methodCount = 0;
        private final boolean hasDynamicLoading = false;
        private final boolean hasReflection = false;
        private final boolean hasInsecureNetwork = false;
        private final boolean isHighlyObfuscated = false;
        
        public DexFeatures(int classCount, int methodCount, boolean hasDynamicLoading, boolean hasReflection, boolean hasInsecureNetwork, boolean isHighlyObfuscated) {
            super();
        }
        
        public final int getClassCount() {
            return 0;
        }
        
        public final int getMethodCount() {
            return 0;
        }
        
        public final boolean getHasDynamicLoading() {
            return false;
        }
        
        public final boolean getHasReflection() {
            return false;
        }
        
        public final boolean getHasInsecureNetwork() {
            return false;
        }
        
        public final boolean isHighlyObfuscated() {
            return false;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final boolean component3() {
            return false;
        }
        
        public final boolean component4() {
            return false;
        }
        
        public final boolean component5() {
            return false;
        }
        
        public final boolean component6() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.DexFeatureExtractor.DexFeatures copy(int classCount, int methodCount, boolean hasDynamicLoading, boolean hasReflection, boolean hasInsecureNetwork, boolean isHighlyObfuscated) {
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