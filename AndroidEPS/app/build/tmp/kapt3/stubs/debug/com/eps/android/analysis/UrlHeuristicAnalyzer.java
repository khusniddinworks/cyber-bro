package com.eps.android.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005J\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/eps/android/analysis/UrlHeuristicAnalyzer;", "", "()V", "dangerousTlds", "", "", "trustedDomains", "trustedSuffixes", "", "analyze", "Lcom/eps/android/analysis/UrlHeuristicAnalyzer$HeuristicResult;", "url", "extractDomainManual", "HeuristicResult", "app_debug"})
public final class UrlHeuristicAnalyzer {
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.String> dangerousTlds = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.String> trustedDomains = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> trustedSuffixes = null;
    @org.jetbrains.annotations.NotNull
    public static final com.eps.android.analysis.UrlHeuristicAnalyzer INSTANCE = null;
    
    private UrlHeuristicAnalyzer() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.UrlHeuristicAnalyzer.HeuristicResult analyze(@org.jetbrains.annotations.NotNull
    java.lang.String url) {
        return null;
    }
    
    private final java.lang.String extractDomainManual(java.lang.String url) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u001f\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0012"}, d2 = {"Lcom/eps/android/analysis/UrlHeuristicAnalyzer$HeuristicResult;", "", "isSuspicious", "", "reason", "", "(ZLjava/lang/String;)V", "()Z", "getReason", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class HeuristicResult {
        private final boolean isSuspicious = false;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String reason = null;
        
        public HeuristicResult(boolean isSuspicious, @org.jetbrains.annotations.Nullable
        java.lang.String reason) {
            super();
        }
        
        public final boolean isSuspicious() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getReason() {
            return null;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.UrlHeuristicAnalyzer.HeuristicResult copy(boolean isSuspicious, @org.jetbrains.annotations.Nullable
        java.lang.String reason) {
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