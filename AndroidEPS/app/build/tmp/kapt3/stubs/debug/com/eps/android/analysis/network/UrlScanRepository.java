package com.eps.android.analysis.network;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001:\u0001\u001eB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0018\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001b\u0010\u0017\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0018\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J!\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ\u001b\u0010\u001d\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0012\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001f"}, d2 = {"Lcom/eps/android/analysis/network/UrlScanRepository;", "", "urlScanCacheDao", "Lcom/eps/android/data/UrlScanCacheDao;", "(Lcom/eps/android/data/UrlScanCacheDao;)V", "api", "Lcom/eps/android/analysis/network/UrlScanApi;", "kotlin.jvm.PlatformType", "apiKey", "", "client", "Lokhttp3/OkHttpClient;", "keyPart1", "keyPart2", "trustedDomains", "", "checkUrl", "Lcom/eps/android/analysis/network/UrlScanRepository$AnalysisResult;", "url", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fallbackResult", "heuristic", "Lcom/eps/android/analysis/UrlHeuristicAnalyzer$HeuristicResult;", "getResult", "uuid", "saveToCache", "", "result", "(Ljava/lang/String;Lcom/eps/android/analysis/network/UrlScanRepository$AnalysisResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitUrl", "AnalysisResult", "app_debug"})
public final class UrlScanRepository {
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.data.UrlScanCacheDao urlScanCacheDao = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String keyPart1 = "019c6d15-83dd-747";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String keyPart2 = "c-bcdd-6541f2229e54";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String apiKey = null;
    @org.jetbrains.annotations.NotNull
    private final okhttp3.OkHttpClient client = null;
    private final com.eps.android.analysis.network.UrlScanApi api = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> trustedDomains = null;
    
    @javax.inject.Inject
    public UrlScanRepository(@org.jetbrains.annotations.NotNull
    com.eps.android.data.UrlScanCacheDao urlScanCacheDao) {
        super();
    }
    
    /**
     * Checks URL using urlscan.io
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object checkUrl(@org.jetbrains.annotations.NotNull
    java.lang.String url, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.eps.android.analysis.network.UrlScanRepository.AnalysisResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object submitUrl(@org.jetbrains.annotations.NotNull
    java.lang.String url, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getResult(@org.jetbrains.annotations.NotNull
    java.lang.String uuid, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.eps.android.analysis.network.UrlScanRepository.AnalysisResult> $completion) {
        return null;
    }
    
    private final com.eps.android.analysis.network.UrlScanRepository.AnalysisResult fallbackResult(java.lang.String url, com.eps.android.analysis.UrlHeuristicAnalyzer.HeuristicResult heuristic) {
        return null;
    }
    
    private final java.lang.Object saveToCache(java.lang.String url, com.eps.android.analysis.network.UrlScanRepository.AnalysisResult result, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0015\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003JQ\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u00032\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u000bH\u00d6\u0001R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\rR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f\u00a8\u0006 "}, d2 = {"Lcom/eps/android/analysis/network/UrlScanRepository$AnalysisResult;", "", "isSecure", "", "maliciousCount", "", "suspiciousCount", "scannerCount", "isUnknown", "isHeuristicResult", "screenshotUrl", "", "(ZIIIZZLjava/lang/String;)V", "()Z", "getMaliciousCount", "()I", "getScannerCount", "getScreenshotUrl", "()Ljava/lang/String;", "getSuspiciousCount", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
    public static final class AnalysisResult {
        private final boolean isSecure = false;
        private final int maliciousCount = 0;
        private final int suspiciousCount = 0;
        private final int scannerCount = 0;
        private final boolean isUnknown = false;
        private final boolean isHeuristicResult = false;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String screenshotUrl = null;
        
        public AnalysisResult(boolean isSecure, int maliciousCount, int suspiciousCount, int scannerCount, boolean isUnknown, boolean isHeuristicResult, @org.jetbrains.annotations.Nullable
        java.lang.String screenshotUrl) {
            super();
        }
        
        public final boolean isSecure() {
            return false;
        }
        
        public final int getMaliciousCount() {
            return 0;
        }
        
        public final int getSuspiciousCount() {
            return 0;
        }
        
        public final int getScannerCount() {
            return 0;
        }
        
        public final boolean isUnknown() {
            return false;
        }
        
        public final boolean isHeuristicResult() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getScreenshotUrl() {
            return null;
        }
        
        public final boolean component1() {
            return false;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final int component4() {
            return 0;
        }
        
        public final boolean component5() {
            return false;
        }
        
        public final boolean component6() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.network.UrlScanRepository.AnalysisResult copy(boolean isSecure, int maliciousCount, int suspiciousCount, int scannerCount, boolean isUnknown, boolean isHeuristicResult, @org.jetbrains.annotations.Nullable
        java.lang.String screenshotUrl) {
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