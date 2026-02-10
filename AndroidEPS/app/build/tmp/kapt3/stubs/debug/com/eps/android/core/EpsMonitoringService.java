package com.eps.android.core;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001aH\u0016J\b\u0010 \u001a\u00020\u001aH\u0016J\"\u0010!\u001a\u00020\u00062\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0006H\u0016J\b\u0010$\u001a\u00020\u001aH\u0002J\b\u0010%\u001a\u00020\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\r\u001a\u00020\u000e8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/eps/android/core/EpsMonitoringService;", "Landroid/app/Service;", "()V", "CHANNEL_ID", "", "NOTIFICATION_ID", "", "eventLogger", "Lcom/eps/android/data/EventLogger;", "getEventLogger", "()Lcom/eps/android/data/EventLogger;", "setEventLogger", "(Lcom/eps/android/data/EventLogger;)V", "fileMonitor", "Lcom/eps/android/core/FileMonitor;", "getFileMonitor", "()Lcom/eps/android/core/FileMonitor;", "setFileMonitor", "(Lcom/eps/android/core/FileMonitor;)V", "quickScanReceiver", "Landroid/content/BroadcastReceiver;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "createNotification", "Landroid/app/Notification;", "createNotificationChannel", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "performManualCheck", "startMonitoringAllDownloadPaths", "app_debug"})
public final class EpsMonitoringService extends android.app.Service {
    @javax.inject.Inject
    public com.eps.android.core.FileMonitor fileMonitor;
    @javax.inject.Inject
    public com.eps.android.data.EventLogger eventLogger;
    private final int NOTIFICATION_ID = 202;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String CHANNEL_ID = "hackdefender_core_channel";
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.BroadcastReceiver quickScanReceiver = null;
    
    public EpsMonitoringService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.core.FileMonitor getFileMonitor() {
        return null;
    }
    
    public final void setFileMonitor(@org.jetbrains.annotations.NotNull
    com.eps.android.core.FileMonitor p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.EventLogger getEventLogger() {
        return null;
    }
    
    public final void setEventLogger(@org.jetbrains.annotations.NotNull
    com.eps.android.data.EventLogger p0) {
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    private final void performManualCheck() {
    }
    
    private final void startMonitoringAllDownloadPaths() {
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    private final void createNotificationChannel() {
    }
}