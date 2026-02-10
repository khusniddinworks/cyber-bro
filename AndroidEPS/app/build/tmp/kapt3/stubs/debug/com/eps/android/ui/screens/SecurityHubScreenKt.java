package com.eps.android.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a6\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a,\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\t2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a&\u0010\u0012\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a$\u0010\u0015\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00160\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a*\u0010\u0017\u001a\u00020\u00012\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u00a8\u0006\u001d"}, d2 = {"AccessMetricCard", "", "modifier", "Landroidx/compose/ui/Modifier;", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "count", "", "label", "", "onClick", "Lkotlin/Function0;", "AppListDialog", "title", "apps", "", "Lcom/eps/android/ui/viewmodel/SecurityHubViewModel$AppInfo;", "onDismiss", "AuditItem", "isSecure", "", "HarmfulAppsDialog", "Lcom/eps/android/ui/viewmodel/AppAuditViewModel$AppAuditInfo;", "SecurityHubScreen", "viewModel", "Lcom/eps/android/ui/viewmodel/SecurityHubViewModel;", "auditViewModel", "Lcom/eps/android/ui/viewmodel/AppAuditViewModel;", "onOpenScanner", "app_debug"})
public final class SecurityHubScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void SecurityHubScreen(@org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.SecurityHubViewModel viewModel, @org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.AppAuditViewModel auditViewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenScanner) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AccessMetricCard(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.vector.ImageVector icon, int count, @org.jetbrains.annotations.NotNull
    java.lang.String label, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AppListDialog(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> apps, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void HarmfulAppsDialog(@org.jetbrains.annotations.NotNull
    java.util.List<com.eps.android.ui.viewmodel.AppAuditViewModel.AppAuditInfo> apps, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AuditItem(@org.jetbrains.annotations.NotNull
    java.lang.String label, boolean isSecure, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}