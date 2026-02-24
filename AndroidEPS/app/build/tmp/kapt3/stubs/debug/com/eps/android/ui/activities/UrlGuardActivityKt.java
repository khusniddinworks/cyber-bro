package com.eps.android.ui.activities;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aN\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a\b\u0010\r\u001a\u00020\u0001H\u0007\u001a\u0010\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a:\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001aD\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\b2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007\u00a8\u0006\u0018"}, d2 = {"DangerousView", "", "url", "", "malicious", "", "total", "isHeuristic", "", "screenshotUrl", "onBack", "Lkotlin/Function0;", "onProceed", "SafeView", "ScanningView", "UnverifiedView", "isRateLimited", "onRetry", "UrlGuardScreen", "isAutoScan", "onVerified", "Lkotlin/Function1;", "urlScanRepository", "Lcom/eps/android/analysis/network/UrlScanRepository;", "app_debug"})
public final class UrlGuardActivityKt {
    
    @androidx.compose.runtime.Composable
    public static final void UrlGuardScreen(@org.jetbrains.annotations.NotNull
    java.lang.String url, boolean isAutoScan, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onVerified, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.network.UrlScanRepository urlScanRepository) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ScanningView(@org.jetbrains.annotations.NotNull
    java.lang.String url) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SafeView() {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DangerousView(@org.jetbrains.annotations.NotNull
    java.lang.String url, int malicious, int total, boolean isHeuristic, @org.jetbrains.annotations.Nullable
    java.lang.String screenshotUrl, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onProceed) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void UnverifiedView(boolean isRateLimited, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onProceed, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onRetry) {
    }
}