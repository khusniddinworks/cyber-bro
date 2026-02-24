package com.eps.android.ui.activities;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0005H\u0002J\u001c\u0010\u0012\u001a\u00020\u0013*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0013H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lcom/eps/android/ui/activities/UrlGuardActivity;", "Landroidx/activity/ComponentActivity;", "()V", "trustedDomains", "", "", "urlScanRepository", "Lcom/eps/android/analysis/network/UrlScanRepository;", "getUrlScanRepository", "()Lcom/eps/android/analysis/network/UrlScanRepository;", "setUrlScanRepository", "(Lcom/eps/android/analysis/network/UrlScanRepository;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "openInBrowser", "url", "getBooleanOfDefault", "", "Landroid/content/Intent;", "key", "default", "app_debug"})
public final class UrlGuardActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject
    public com.eps.android.analysis.network.UrlScanRepository urlScanRepository;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> trustedDomains = null;
    
    public UrlGuardActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.network.UrlScanRepository getUrlScanRepository() {
        return null;
    }
    
    public final void setUrlScanRepository(@org.jetbrains.annotations.NotNull
    com.eps.android.analysis.network.UrlScanRepository p0) {
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final boolean getBooleanOfDefault(android.content.Intent $this$getBooleanOfDefault, java.lang.String key, boolean p2_772401952) {
        return false;
    }
    
    private final void openInBrowser(java.lang.String url) {
    }
}