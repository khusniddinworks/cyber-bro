package com.eps.android.core;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0007\u0018\u0000 92\u00020\u0001:\u00019B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000bH\u0002J\u0010\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u0010&\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u000bH\u0002J\u0016\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010!\u001a\u00020\"H\u0002J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010$\u001a\u00020%H\u0002J\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010*\u001a\u00020\u000bH\u0002J)\u0010+\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000bH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010-J!\u0010.\u001a\u00020/2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u000bH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J\b\u00101\u001a\u00020\u001dH\u0002J\u0010\u00102\u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0016J\b\u00103\u001a\u00020\u001dH\u0016J\b\u00104\u001a\u00020\u001dH\u0016J\b\u00105\u001a\u00020\u001dH\u0014J \u00106\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u000bH\u0002J\u0010\u00107\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\u000bH\u0002J\b\u00108\u001a\u00020\u001dH\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0019\u001a\n \u001b*\u0004\u0018\u00010\u001a0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006:"}, d2 = {"Lcom/eps/android/core/PhishingAccessibilityService;", "Landroid/accessibilityservice/AccessibilityService;", "()V", "blacklistDao", "Lcom/eps/android/data/BlacklistDao;", "getBlacklistDao", "()Lcom/eps/android/data/BlacklistDao;", "setBlacklistDao", "(Lcom/eps/android/data/BlacklistDao;)V", "cachedBlacklist", "", "", "dangerousTlds", "", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "suspiciousPatterns", "", "Lkotlin/text/Regex;", "threatEventDao", "Lcom/eps/android/data/ThreatEventDao;", "getThreatEventDao", "()Lcom/eps/android/data/ThreatEventDao;", "setThreatEventDao", "(Lcom/eps/android/data/ThreatEventDao;)V", "urlPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "checkUrl", "", "url", "sourceApp", "extractAllTextFromEvent", "event", "Landroid/view/accessibility/AccessibilityEvent;", "extractAllTextFromNode", "node", "Landroid/view/accessibility/AccessibilityNodeInfo;", "extractDomain", "extractUrlsFromEvent", "extractUrlsFromNode", "extractUrlsFromText", "text", "handlePhishingUrl", "domain", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isPhishingUrl", "", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadBlacklist", "onAccessibilityEvent", "onDestroy", "onInterrupt", "onServiceConnected", "showPhishingWarning", "showVishingWarning", "triggerBlockAction", "Companion", "app_debug"})
public final class PhishingAccessibilityService extends android.accessibilityservice.AccessibilityService {
    @javax.inject.Inject
    public com.eps.android.data.BlacklistDao blacklistDao;
    @javax.inject.Inject
    public com.eps.android.data.ThreatEventDao threatEventDao;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    private final java.util.regex.Pattern urlPattern = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<kotlin.text.Regex> suspiciousPatterns = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> dangerousTlds = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> cachedBlacklist = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.String> tempWhitelist = null;
    @org.jetbrains.annotations.NotNull
    public static final com.eps.android.core.PhishingAccessibilityService.Companion Companion = null;
    
    public PhishingAccessibilityService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.BlacklistDao getBlacklistDao() {
        return null;
    }
    
    public final void setBlacklistDao(@org.jetbrains.annotations.NotNull
    com.eps.android.data.BlacklistDao p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.ThreatEventDao getThreatEventDao() {
        return null;
    }
    
    public final void setThreatEventDao(@org.jetbrains.annotations.NotNull
    com.eps.android.data.ThreatEventDao p0) {
    }
    
    @java.lang.Override
    protected void onServiceConnected() {
    }
    
    private final void loadBlacklist() {
    }
    
    @java.lang.Override
    public void onAccessibilityEvent(@org.jetbrains.annotations.NotNull
    android.view.accessibility.AccessibilityEvent event) {
    }
    
    private final java.lang.String extractAllTextFromEvent(android.view.accessibility.AccessibilityEvent event) {
        return null;
    }
    
    private final java.lang.String extractAllTextFromNode(android.view.accessibility.AccessibilityNodeInfo node) {
        return null;
    }
    
    private final java.util.Set<java.lang.String> extractUrlsFromEvent(android.view.accessibility.AccessibilityEvent event) {
        return null;
    }
    
    private final java.util.Set<java.lang.String> extractUrlsFromNode(android.view.accessibility.AccessibilityNodeInfo node) {
        return null;
    }
    
    private final java.util.Set<java.lang.String> extractUrlsFromText(java.lang.String text) {
        return null;
    }
    
    private final void checkUrl(java.lang.String url, java.lang.String sourceApp) {
    }
    
    private final java.lang.String extractDomain(java.lang.String url) {
        return null;
    }
    
    private final java.lang.Object isPhishingUrl(java.lang.String url, java.lang.String domain, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object handlePhishingUrl(java.lang.String url, java.lang.String domain, java.lang.String sourceApp, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void showPhishingWarning(java.lang.String url, java.lang.String domain, java.lang.String sourceApp) {
    }
    
    private final void triggerBlockAction() {
    }
    
    private final void showVishingWarning(java.lang.String text) {
    }
    
    @java.lang.Override
    public void onInterrupt() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u000b"}, d2 = {"Lcom/eps/android/core/PhishingAccessibilityService$Companion;", "", "()V", "tempWhitelist", "", "", "getTempWhitelist", "()Ljava/util/Set;", "addToWhitelist", "", "domain", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getTempWhitelist() {
            return null;
        }
        
        public final void addToWhitelist(@org.jetbrains.annotations.NotNull
        java.lang.String domain) {
        }
    }
}