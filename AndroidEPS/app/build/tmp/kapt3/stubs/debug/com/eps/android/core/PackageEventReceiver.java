package com.eps.android.core;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/eps/android/core/PackageEventReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "eventLogger", "Lcom/eps/android/data/EventLogger;", "getEventLogger", "()Lcom/eps/android/data/EventLogger;", "setEventLogger", "(Lcom/eps/android/data/EventLogger;)V", "packageAnalysisManager", "Lcom/eps/android/analysis/PackageAnalysisManager;", "getPackageAnalysisManager", "()Lcom/eps/android/analysis/PackageAnalysisManager;", "setPackageAnalysisManager", "(Lcom/eps/android/analysis/PackageAnalysisManager;)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_debug"})
public final class PackageEventReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject
    public com.eps.android.analysis.PackageAnalysisManager packageAnalysisManager;
    @javax.inject.Inject
    public com.eps.android.data.EventLogger eventLogger;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public PackageEventReceiver() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.PackageAnalysisManager getPackageAnalysisManager() {
        return null;
    }
    
    public final void setPackageAnalysisManager(@org.jetbrains.annotations.NotNull
    com.eps.android.analysis.PackageAnalysisManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.EventLogger getEventLogger() {
        return null;
    }
    
    public final void setEventLogger(@org.jetbrains.annotations.NotNull
    com.eps.android.data.EventLogger p0) {
    }
    
    @java.lang.Override
    public void onReceive(@org.jetbrains.annotations.Nullable
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
    }
}