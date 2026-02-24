package com.eps.android.core;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0002J\b\u0010!\u001a\u00020\u001dH\u0016J\b\u0010\"\u001a\u00020\u001dH\u0016J\u0012\u0010#\u001a\u00020\u001d2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\u0010\u0010&\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u001fH\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u00020\u000f8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0014\u001a\u00020\u00158\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/eps/android/core/EpsNotificationListener;", "Landroid/service/notification/NotificationListenerService;", "()V", "eventLogger", "Lcom/eps/android/data/EventLogger;", "getEventLogger", "()Lcom/eps/android/data/EventLogger;", "setEventLogger", "(Lcom/eps/android/data/EventLogger;)V", "keywordPattern", "Lkotlin/text/Regex;", "numberPattern", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "threatNotifier", "Lcom/eps/android/core/ThreatNotifier;", "getThreatNotifier", "()Lcom/eps/android/core/ThreatNotifier;", "setThreatNotifier", "(Lcom/eps/android/core/ThreatNotifier;)V", "urlScanRepository", "Lcom/eps/android/analysis/network/UrlScanRepository;", "getUrlScanRepository", "()Lcom/eps/android/analysis/network/UrlScanRepository;", "setUrlScanRepository", "(Lcom/eps/android/analysis/network/UrlScanRepository;)V", "vibrationReceiver", "Landroid/content/BroadcastReceiver;", "extractAndScanLinks", "", "content", "", "sourceApp", "onCreate", "onDestroy", "onNotificationPosted", "sbn", "Landroid/service/notification/StatusBarNotification;", "triggerVishingPush", "app_debug"})
public final class EpsNotificationListener extends android.service.notification.NotificationListenerService {
    @javax.inject.Inject
    public com.eps.android.analysis.network.UrlScanRepository urlScanRepository;
    @javax.inject.Inject
    public com.eps.android.data.EventLogger eventLogger;
    @javax.inject.Inject
    public com.eps.android.core.ThreatNotifier threatNotifier;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.text.Regex keywordPattern = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.text.Regex numberPattern = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.BroadcastReceiver vibrationReceiver = null;
    
    public EpsNotificationListener() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.network.UrlScanRepository getUrlScanRepository() {
        return null;
    }
    
    public final void setUrlScanRepository(@org.jetbrains.annotations.NotNull
    com.eps.android.analysis.network.UrlScanRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.EventLogger getEventLogger() {
        return null;
    }
    
    public final void setEventLogger(@org.jetbrains.annotations.NotNull
    com.eps.android.data.EventLogger p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.core.ThreatNotifier getThreatNotifier() {
        return null;
    }
    
    public final void setThreatNotifier(@org.jetbrains.annotations.NotNull
    com.eps.android.core.ThreatNotifier p0) {
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @java.lang.Override
    public void onNotificationPosted(@org.jetbrains.annotations.Nullable
    android.service.notification.StatusBarNotification sbn) {
    }
    
    private final void extractAndScanLinks(java.lang.String content, java.lang.String sourceApp) {
    }
    
    private final void triggerVishingPush(java.lang.String sourceApp) {
    }
}