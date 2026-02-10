package com.eps.android.core;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\b\u0007\u0018\u00002\u00020\u0001B3\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0011\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015H\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0016"}, d2 = {"Lcom/eps/android/core/EpsWorker;", "Landroidx/work/CoroutineWorker;", "appContext", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "staticAnalyzer", "Lcom/eps/android/analysis/StaticAnalyzer;", "threatEventDao", "Lcom/eps/android/data/ThreatEventDao;", "blacklistDao", "Lcom/eps/android/data/BlacklistDao;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;Lcom/eps/android/analysis/StaticAnalyzer;Lcom/eps/android/data/ThreatEventDao;Lcom/eps/android/data/BlacklistDao;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findApks", "", "dir", "Ljava/io/File;", "list", "", "app_debug"})
@androidx.hilt.work.HiltWorker
public final class EpsWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.StaticAnalyzer staticAnalyzer = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.data.ThreatEventDao threatEventDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.data.BlacklistDao blacklistDao = null;
    
    @dagger.assisted.AssistedInject
    public EpsWorker(@dagger.assisted.Assisted
    @org.jetbrains.annotations.NotNull
    android.content.Context appContext, @dagger.assisted.Assisted
    @org.jetbrains.annotations.NotNull
    androidx.work.WorkerParameters workerParams, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.StaticAnalyzer staticAnalyzer, @org.jetbrains.annotations.NotNull
    com.eps.android.data.ThreatEventDao threatEventDao, @org.jetbrains.annotations.NotNull
    com.eps.android.data.BlacklistDao blacklistDao) {
        super(null, null);
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> $completion) {
        return null;
    }
    
    private final void findApks(java.io.File dir, java.util.List<java.io.File> list) {
    }
}