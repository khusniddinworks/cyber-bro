package com.eps.android.core;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\nJ\u000e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\rJ\u0006\u0010\u001b\u001a\u00020\u0015J\u0010\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/eps/android/core/FileMonitor;", "", "staticAnalyzer", "Lcom/eps/android/analysis/StaticAnalyzer;", "eventLogger", "Lcom/eps/android/data/EventLogger;", "threatNotifier", "Lcom/eps/android/core/ThreatNotifier;", "(Lcom/eps/android/analysis/StaticAnalyzer;Lcom/eps/android/data/EventLogger;Lcom/eps/android/core/ThreatNotifier;)V", "isMonitoring", "", "lastAnalyzed", "", "", "", "observers", "", "Landroid/os/FileObserver;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "analyzeIncomingFile", "", "file", "Ljava/io/File;", "isActive", "startMonitoring", "path", "stopMonitoring", "triggerAnalysis", "app_debug"})
public final class FileMonitor {
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.StaticAnalyzer staticAnalyzer = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.data.EventLogger eventLogger = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.core.ThreatNotifier threatNotifier = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<android.os.FileObserver> observers = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    private boolean isMonitoring = false;
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.String, java.lang.Long> lastAnalyzed = null;
    
    @javax.inject.Inject
    public FileMonitor(@org.jetbrains.annotations.NotNull
    com.eps.android.analysis.StaticAnalyzer staticAnalyzer, @org.jetbrains.annotations.NotNull
    com.eps.android.data.EventLogger eventLogger, @org.jetbrains.annotations.NotNull
    com.eps.android.core.ThreatNotifier threatNotifier) {
        super();
    }
    
    public final void startMonitoring(@org.jetbrains.annotations.NotNull
    java.lang.String path) {
    }
    
    private final void triggerAnalysis(java.io.File file) {
    }
    
    public final void analyzeIncomingFile(@org.jetbrains.annotations.NotNull
    java.io.File file) {
    }
    
    public final void stopMonitoring() {
    }
    
    public final boolean isActive() {
        return false;
    }
}