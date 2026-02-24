package com.eps.android.analysis;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001:\u0002\u0011\u0012B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J;\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u001e\u0010\u000f\u001a\u00020\b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"Lcom/eps/android/analysis/AppRiskAuditor;", "", "trustedAppDao", "Lcom/eps/android/data/TrustedAppDao;", "(Lcom/eps/android/data/TrustedAppDao;)V", "auditPermissions", "Lcom/eps/android/analysis/AppRiskAuditor$RiskReport;", "packageName", "", "permissions", "", "isSystem", "", "isPlayStore", "(Ljava/lang/String;Ljava/util/List;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildExplanation", "flags", "RiskLevel", "RiskReport", "app_debug"})
public final class AppRiskAuditor {
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.data.TrustedAppDao trustedAppDao = null;
    
    @javax.inject.Inject
    public AppRiskAuditor(@org.jetbrains.annotations.NotNull
    com.eps.android.data.TrustedAppDao trustedAppDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object auditPermissions(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> permissions, boolean isSystem, boolean isPlayStore, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.eps.android.analysis.AppRiskAuditor.RiskReport> $completion) {
        return null;
    }
    
    private final java.lang.String buildExplanation(java.util.List<java.lang.String> flags, boolean isSystem) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/eps/android/analysis/AppRiskAuditor$RiskLevel;", "", "(Ljava/lang/String;I)V", "LOW", "MEDIUM", "HIGH", "CRITICAL", "app_debug"})
    public static enum RiskLevel {
        /*public static final*/ LOW /* = new LOW() */,
        /*public static final*/ MEDIUM /* = new MEDIUM() */,
        /*public static final*/ HIGH /* = new HIGH() */,
        /*public static final*/ CRITICAL /* = new CRITICAL() */;
        
        RiskLevel() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.eps.android.analysis.AppRiskAuditor.RiskLevel> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003JG\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001f\u001a\u00020\bH\u00d6\u0001R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006 "}, d2 = {"Lcom/eps/android/analysis/AppRiskAuditor$RiskReport;", "", "level", "Lcom/eps/android/analysis/AppRiskAuditor$RiskLevel;", "score", "", "flags", "", "", "explanation", "dangerousPermissions", "(Lcom/eps/android/analysis/AppRiskAuditor$RiskLevel;ILjava/util/List;Ljava/lang/String;Ljava/util/List;)V", "getDangerousPermissions", "()Ljava/util/List;", "getExplanation", "()Ljava/lang/String;", "getFlags", "getLevel", "()Lcom/eps/android/analysis/AppRiskAuditor$RiskLevel;", "getScore", "()I", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class RiskReport {
        @org.jetbrains.annotations.NotNull
        private final com.eps.android.analysis.AppRiskAuditor.RiskLevel level = null;
        private final int score = 0;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> flags = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String explanation = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> dangerousPermissions = null;
        
        public RiskReport(@org.jetbrains.annotations.NotNull
        com.eps.android.analysis.AppRiskAuditor.RiskLevel level, int score, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> flags, @org.jetbrains.annotations.NotNull
        java.lang.String explanation, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> dangerousPermissions) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.AppRiskAuditor.RiskLevel getLevel() {
            return null;
        }
        
        public final int getScore() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getFlags() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getExplanation() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getDangerousPermissions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.AppRiskAuditor.RiskLevel component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.AppRiskAuditor.RiskReport copy(@org.jetbrains.annotations.NotNull
        com.eps.android.analysis.AppRiskAuditor.RiskLevel level, int score, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> flags, @org.jetbrains.annotations.NotNull
        java.lang.String explanation, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> dangerousPermissions) {
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