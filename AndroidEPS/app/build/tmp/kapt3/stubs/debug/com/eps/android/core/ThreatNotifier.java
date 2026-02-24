package com.eps.android.core;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000f\u001a\u00020\u0010J\b\u0010\u0011\u001a\u00020\u0010H\u0002J0\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\bH\u0002J&\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\bJ\u0016\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\bJ.\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\bJ\u0018\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\b2\b\b\u0002\u0010!\u001a\u00020\"R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/eps/android/core/ThreatNotifier;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "ALERT_ID", "", "CHANNEL_ID", "", "VISHING_ALERT_ID", "VISHING_CHANNEL_ID", "activeVibrator", "Landroid/os/Vibrator;", "notificationManager", "Landroid/app/NotificationManager;", "cancelVishingVibration", "", "createNotificationChannels", "getAlertPendingIntent", "Landroid/app/PendingIntent;", "title", "message", "packageName", "risk", "reason", "launchAlertActivity", "appName", "riskLevel", "showInstallAlert", "showThreatAlert", "filePath", "showVishingAlert", "sourceName", "isOtp", "", "Companion", "app_debug"})
public final class ThreatNotifier {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final android.app.NotificationManager notificationManager = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String CHANNEL_ID = "threat_alert_channel";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String VISHING_CHANNEL_ID = "vishing_alert_channel";
    private final int ALERT_ID = 1001;
    private final int VISHING_ALERT_ID = 7777;
    @org.jetbrains.annotations.Nullable
    private android.os.Vibrator activeVibrator;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_STOP_VISH = "com.eps.android.STOP_VISHING_ALERT";
    @org.jetbrains.annotations.NotNull
    public static final com.eps.android.core.ThreatNotifier.Companion Companion = null;
    
    @javax.inject.Inject
    public ThreatNotifier(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final void createNotificationChannels() {
    }
    
    /**
     * Shows a specialized push notification with extended vibration for vishing calls
     */
    public final void showVishingAlert(@org.jetbrains.annotations.NotNull
    java.lang.String sourceName, boolean isOtp) {
    }
    
    public final void cancelVishingVibration() {
    }
    
    /**
     * Shows a high-priority notification for a detected threat (Apps/Files)
     */
    public final void showThreatAlert(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.lang.String packageName, @org.jetbrains.annotations.Nullable
    java.lang.String filePath) {
    }
    
    public final void showInstallAlert(@org.jetbrains.annotations.NotNull
    java.lang.String appName, @org.jetbrains.annotations.NotNull
    java.lang.String packageName) {
    }
    
    public final void launchAlertActivity(@org.jetbrains.annotations.NotNull
    java.lang.String appName, @org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.NotNull
    java.lang.String riskLevel, @org.jetbrains.annotations.NotNull
    java.lang.String reason) {
    }
    
    private final android.app.PendingIntent getAlertPendingIntent(java.lang.String title, java.lang.String message, java.lang.String packageName, java.lang.String risk, java.lang.String reason) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/eps/android/core/ThreatNotifier$Companion;", "", "()V", "ACTION_STOP_VISH", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}