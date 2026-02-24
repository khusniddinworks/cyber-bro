package com.eps.android.core;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0018\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003H\u0007\u001a\u001e\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003\u001a\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001a\u000e\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003\u00a8\u0006\u0013"}, d2 = {"AiScanOverlayScreen", "", "packageName", "", "onClose", "Lkotlin/Function0;", "ApkBlockWarningScreen", "onGoBack", "InfoRow", "label", "value", "VishingWarningScreen", "detectedText", "detectReason", "domain", "url", "findLatestApk", "Ljava/io/File;", "getAppName", "app_debug"})
public final class PhishingWarningActivityKt {
    
    @androidx.compose.runtime.Composable
    public static final void VishingWarningScreen(@org.jetbrains.annotations.NotNull
    java.lang.String detectedText, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onGoBack) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ApkBlockWarningScreen(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onGoBack) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AiScanOverlayScreen(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClose) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void InfoRow(@org.jetbrains.annotations.NotNull
    java.lang.String label, @org.jetbrains.annotations.NotNull
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String getAppName(@org.jetbrains.annotations.NotNull
    java.lang.String packageName) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String detectReason(@org.jetbrains.annotations.NotNull
    java.lang.String domain, @org.jetbrains.annotations.NotNull
    java.lang.String url) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public static final java.io.File findLatestApk() {
        return null;
    }
}