package com.eps.android.core;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0005\b\u0007\u0018\u0000 V2\u00020\u0001:\u0001VB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\r2\u0006\u00104\u001a\u00020\rH\u0002J\u0010\u00105\u001a\u00020\r2\u0006\u00106\u001a\u000207H\u0002J\u0010\u00108\u001a\u00020\r2\u0006\u00109\u001a\u00020:H\u0002J\u0010\u0010;\u001a\u00020\r2\u0006\u00103\u001a\u00020\rH\u0002J\u0016\u0010<\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u0006\u00109\u001a\u00020:H\u0002J\u0016\u0010=\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u0006\u00106\u001a\u000207H\u0002J\u0016\u0010>\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u0006\u0010?\u001a\u00020\rH\u0002J)\u0010@\u001a\u0002022\u0006\u00103\u001a\u00020\r2\u0006\u0010A\u001a\u00020\r2\u0006\u00104\u001a\u00020\rH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010BJ!\u0010C\u001a\u00020D2\u0006\u00103\u001a\u00020\r2\u0006\u0010A\u001a\u00020\rH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010EJ\b\u0010F\u001a\u000202H\u0002J\u0010\u0010G\u001a\u0002022\u0006\u00106\u001a\u000207H\u0016J\b\u0010H\u001a\u000202H\u0016J\b\u0010I\u001a\u000202H\u0016J\b\u0010J\u001a\u000202H\u0014J1\u0010K\u001a\u0002022\u0006\u00103\u001a\u00020\r2\u0006\u0010A\u001a\u00020\r2\u0006\u00104\u001a\u00020\r2\u0006\u0010L\u001a\u00020\rH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010MJ \u0010N\u001a\u0002022\u0006\u00103\u001a\u00020\r2\u0006\u0010A\u001a\u00020\r2\u0006\u00104\u001a\u00020\rH\u0002J\u0010\u0010O\u001a\u0002022\u0006\u0010?\u001a\u00020\rH\u0002J\"\u0010P\u001a\u0002022\u0006\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020\r2\b\b\u0002\u0010T\u001a\u00020DH\u0002J\u0010\u0010U\u001a\u0002022\u0006\u0010S\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001d\u001a\u00020\u001e8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001e\u0010#\u001a\u00020$8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020\r0\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010*\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010+\u001a\u00020,8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006W"}, d2 = {"Lcom/eps/android/core/PhishingAccessibilityService;", "Landroid/accessibilityservice/AccessibilityService;", "()V", "SCAN_COOLDOWN_MS", "", "blacklistDao", "Lcom/eps/android/data/BlacklistDao;", "getBlacklistDao", "()Lcom/eps/android/data/BlacklistDao;", "setBlacklistDao", "(Lcom/eps/android/data/BlacklistDao;)V", "cachedBlacklist", "", "", "dangerousTlds", "", "domainPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "forbiddenDomains", "ignoredDomains", "ignoredExtensions", "lastForegroundPackage", "lastScanTime", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "suspiciousPatterns", "", "Lkotlin/text/Regex;", "threatEventDao", "Lcom/eps/android/data/ThreatEventDao;", "getThreatEventDao", "()Lcom/eps/android/data/ThreatEventDao;", "setThreatEventDao", "(Lcom/eps/android/data/ThreatEventDao;)V", "threatNotifier", "Lcom/eps/android/core/ThreatNotifier;", "getThreatNotifier", "()Lcom/eps/android/core/ThreatNotifier;", "setThreatNotifier", "(Lcom/eps/android/core/ThreatNotifier;)V", "trustedDomainSuffixes", "urlPattern", "urlScanRepository", "Lcom/eps/android/analysis/network/UrlScanRepository;", "getUrlScanRepository", "()Lcom/eps/android/analysis/network/UrlScanRepository;", "setUrlScanRepository", "(Lcom/eps/android/analysis/network/UrlScanRepository;)V", "checkUrl", "", "url", "sourceApp", "extractAllTextFromEvent", "event", "Landroid/view/accessibility/AccessibilityEvent;", "extractAllTextFromNode", "node", "Landroid/view/accessibility/AccessibilityNodeInfo;", "extractDomain", "extractUrlsAndSpans", "extractUrlsFromEvent", "extractUrlsFromText", "text", "handlePhishingUrl", "domain", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isPhishingUrl", "", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadBlacklist", "onAccessibilityEvent", "onDestroy", "onInterrupt", "onServiceConnected", "recordThreat", "reason", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "showPhishingWarning", "showVishingWarning", "triggerAiScan", "windowId", "", "packageName", "shouldCache", "triggerBlockAction", "Companion", "app_debug"})
public final class PhishingAccessibilityService extends android.accessibilityservice.AccessibilityService {
    @javax.inject.Inject
    public com.eps.android.data.BlacklistDao blacklistDao;
    @javax.inject.Inject
    public com.eps.android.data.ThreatEventDao threatEventDao;
    @javax.inject.Inject
    public com.eps.android.analysis.network.UrlScanRepository urlScanRepository;
    @javax.inject.Inject
    public com.eps.android.core.ThreatNotifier threatNotifier;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    private final java.util.regex.Pattern urlPattern = null;
    private final java.util.regex.Pattern domainPattern = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> ignoredExtensions = null;
    private long lastScanTime = 0L;
    private final long SCAN_COOLDOWN_MS = 1500L;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<kotlin.text.Regex> suspiciousPatterns = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> dangerousTlds = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> ignoredDomains = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> trustedDomainSuffixes = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> forbiddenDomains = null;
    @org.jetbrains.annotations.Nullable
    private java.lang.String lastForegroundPackage;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> cachedBlacklist = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.String> tempWhitelist = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.Integer> scannedWindowIds = null;
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
    protected void onServiceConnected() {
    }
    
    private final void loadBlacklist() {
    }
    
    @java.lang.Override
    public void onAccessibilityEvent(@org.jetbrains.annotations.NotNull
    android.view.accessibility.AccessibilityEvent event) {
    }
    
    private final java.lang.Object recordThreat(java.lang.String url, java.lang.String domain, java.lang.String sourceApp, java.lang.String reason, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
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
    
    private final java.util.Set<java.lang.String> extractUrlsAndSpans(android.view.accessibility.AccessibilityNodeInfo node) {
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
    
    private final void triggerAiScan(int windowId, java.lang.String packageName, boolean shouldCache) {
    }
    
    private final void triggerBlockAction(java.lang.String packageName) {
    }
    
    private final void showVishingWarning(java.lang.String text) {
    }
    
    @java.lang.Override
    public void onInterrupt() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\r"}, d2 = {"Lcom/eps/android/core/PhishingAccessibilityService$Companion;", "", "()V", "scannedWindowIds", "", "", "tempWhitelist", "", "getTempWhitelist", "()Ljava/util/Set;", "addToWhitelist", "", "domain", "app_debug"})
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