package com.eps.android.core;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020%H\u0002J\u0018\u0010&\u001a\u00020\u001e2\u0006\u0010\'\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0002J\u0018\u0010(\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\"2\u0006\u0010)\u001a\u00020\"H\u0002J\u0018\u0010*\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010+\u001a\u00020,H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u00020\u00128\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\u00020\u00188\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c\u00a8\u0006-"}, d2 = {"Lcom/eps/android/core/SmsReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "eventLogger", "Lcom/eps/android/data/EventLogger;", "getEventLogger", "()Lcom/eps/android/data/EventLogger;", "setEventLogger", "(Lcom/eps/android/data/EventLogger;)V", "nlpEngine", "Lcom/eps/android/analysis/SmartPhishingNLP;", "getNlpEngine", "()Lcom/eps/android/analysis/SmartPhishingNLP;", "setNlpEngine", "(Lcom/eps/android/analysis/SmartPhishingNLP;)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "threatNotifier", "Lcom/eps/android/core/ThreatNotifier;", "getThreatNotifier", "()Lcom/eps/android/core/ThreatNotifier;", "setThreatNotifier", "(Lcom/eps/android/core/ThreatNotifier;)V", "urlScanRepository", "Lcom/eps/android/analysis/network/UrlScanRepository;", "getUrlScanRepository", "()Lcom/eps/android/analysis/network/UrlScanRepository;", "setUrlScanRepository", "(Lcom/eps/android/analysis/network/UrlScanRepository;)V", "analyzeSms", "", "context", "Landroid/content/Context;", "body", "", "sender", "isOnCall", "", "extractAndCheckUrls", "text", "logThreat", "details", "onReceive", "intent", "Landroid/content/Intent;", "app_debug"})
public final class SmsReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject
    public com.eps.android.analysis.SmartPhishingNLP nlpEngine;
    @javax.inject.Inject
    public com.eps.android.data.EventLogger eventLogger;
    @javax.inject.Inject
    public com.eps.android.analysis.network.UrlScanRepository urlScanRepository;
    @javax.inject.Inject
    public com.eps.android.core.ThreatNotifier threatNotifier;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public SmsReceiver() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.SmartPhishingNLP getNlpEngine() {
        return null;
    }
    
    public final void setNlpEngine(@org.jetbrains.annotations.NotNull
    com.eps.android.analysis.SmartPhishingNLP p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.EventLogger getEventLogger() {
        return null;
    }
    
    public final void setEventLogger(@org.jetbrains.annotations.NotNull
    com.eps.android.data.EventLogger p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.network.UrlScanRepository getUrlScanRepository() {
        return null;
    }
    
    public final void setUrlScanRepository(@org.jetbrains.annotations.NotNull
    com.eps.android.analysis.network.UrlScanRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.core.ThreatNotifier getThreatNotifier() {
        return null;
    }
    
    public final void setThreatNotifier(@org.jetbrains.annotations.NotNull
    com.eps.android.core.ThreatNotifier p0) {
    }
    
    @java.lang.Override
    public void onReceive(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.content.Intent intent) {
    }
    
    private final void analyzeSms(android.content.Context context, java.lang.String body, java.lang.String sender, boolean isOnCall) {
    }
    
    private final void logThreat(java.lang.String sender, java.lang.String details) {
    }
    
    private final void extractAndCheckUrls(java.lang.String text, java.lang.String sender) {
    }
}