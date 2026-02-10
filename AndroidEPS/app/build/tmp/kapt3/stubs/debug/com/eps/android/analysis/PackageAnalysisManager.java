package com.eps.android.analysis;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B)\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0012"}, d2 = {"Lcom/eps/android/analysis/PackageAnalysisManager;", "", "context", "Landroid/content/Context;", "staticAnalyzer", "Lcom/eps/android/analysis/StaticAnalyzer;", "threatNotifier", "Lcom/eps/android/core/ThreatNotifier;", "eventLogger", "Lcom/eps/android/data/EventLogger;", "(Landroid/content/Context;Lcom/eps/android/analysis/StaticAnalyzer;Lcom/eps/android/core/ThreatNotifier;Lcom/eps/android/data/EventLogger;)V", "analyzeNewPackage", "", "packageName", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkWhitelist", "", "app_debug"})
public final class PackageAnalysisManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.StaticAnalyzer staticAnalyzer = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.core.ThreatNotifier threatNotifier = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.data.EventLogger eventLogger = null;
    
    @javax.inject.Inject
    public PackageAnalysisManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.StaticAnalyzer staticAnalyzer, @org.jetbrains.annotations.NotNull
    com.eps.android.core.ThreatNotifier threatNotifier, @org.jetbrains.annotations.NotNull
    com.eps.android.data.EventLogger eventLogger) {
        super();
    }
    
    /**
     * Performs a deep AI audit on a newly installed package
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object analyzeNewPackage(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final boolean checkWhitelist(java.lang.String packageName) {
        return false;
    }
}