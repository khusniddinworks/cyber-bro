package com.eps.android.analysis;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0010B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J!\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2 = {"Lcom/eps/android/analysis/ApkAnalyzer;", "", "auditor", "Lcom/eps/android/analysis/AppRiskAuditor;", "(Lcom/eps/android/analysis/AppRiskAuditor;)V", "analyzeApk", "Lcom/eps/android/analysis/ApkAnalyzer$ApkVerdict;", "context", "Landroid/content/Context;", "file", "Ljava/io/File;", "(Landroid/content/Context;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "analyzeInstalledApp", "packageName", "", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ApkVerdict", "app_debug"})
public final class ApkAnalyzer {
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.AppRiskAuditor auditor = null;
    
    @javax.inject.Inject
    public ApkAnalyzer(@org.jetbrains.annotations.NotNull
    com.eps.android.analysis.AppRiskAuditor auditor) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object analyzeApk(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.io.File file, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.eps.android.analysis.ApkAnalyzer.ApkVerdict> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object analyzeInstalledApp(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.eps.android.analysis.ApkAnalyzer.ApkVerdict> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J-\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/eps/android/analysis/ApkAnalyzer$ApkVerdict;", "", "packageName", "", "permissions", "", "riskReport", "Lcom/eps/android/analysis/AppRiskAuditor$RiskReport;", "(Ljava/lang/String;Ljava/util/List;Lcom/eps/android/analysis/AppRiskAuditor$RiskReport;)V", "getPackageName", "()Ljava/lang/String;", "getPermissions", "()Ljava/util/List;", "getRiskReport", "()Lcom/eps/android/analysis/AppRiskAuditor$RiskReport;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class ApkVerdict {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String packageName = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> permissions = null;
        @org.jetbrains.annotations.NotNull
        private final com.eps.android.analysis.AppRiskAuditor.RiskReport riskReport = null;
        
        public ApkVerdict(@org.jetbrains.annotations.NotNull
        java.lang.String packageName, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> permissions, @org.jetbrains.annotations.NotNull
        com.eps.android.analysis.AppRiskAuditor.RiskReport riskReport) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPackageName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getPermissions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.AppRiskAuditor.RiskReport getRiskReport() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.AppRiskAuditor.RiskReport component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ApkAnalyzer.ApkVerdict copy(@org.jetbrains.annotations.NotNull
        java.lang.String packageName, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> permissions, @org.jetbrains.annotations.NotNull
        com.eps.android.analysis.AppRiskAuditor.RiskReport riskReport) {
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