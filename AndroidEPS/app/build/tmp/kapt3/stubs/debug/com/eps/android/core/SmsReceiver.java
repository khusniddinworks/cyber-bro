package com.eps.android.core;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0010H\u0002J\u0018\u0010\u0016\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J \u0010\u0019\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/eps/android/core/SmsReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "eventLogger", "Lcom/eps/android/data/EventLogger;", "getEventLogger", "()Lcom/eps/android/data/EventLogger;", "setEventLogger", "(Lcom/eps/android/data/EventLogger;)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "analyzeSms", "", "context", "Landroid/content/Context;", "body", "", "sender", "isOnCall", "", "logThreat", "details", "onReceive", "intent", "Landroid/content/Intent;", "triggerVishingAlarm", "app_debug"})
public final class SmsReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject
    public com.eps.android.data.EventLogger eventLogger;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public SmsReceiver() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.EventLogger getEventLogger() {
        return null;
    }
    
    public final void setEventLogger(@org.jetbrains.annotations.NotNull
    com.eps.android.data.EventLogger p0) {
    }
    
    @java.lang.Override
    public void onReceive(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.content.Intent intent) {
    }
    
    private final void analyzeSms(android.content.Context context, java.lang.String body, java.lang.String sender, boolean isOnCall) {
    }
    
    private final void triggerVishingAlarm(android.content.Context context, java.lang.String sender, java.lang.String body) {
    }
    
    private final void logThreat(java.lang.String sender, java.lang.String details) {
    }
}