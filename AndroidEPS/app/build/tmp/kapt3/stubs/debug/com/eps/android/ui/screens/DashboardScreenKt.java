package com.eps.android.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000n\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a;\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000b\u001a$\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u001aD\u0010\u0011\u001a\u00020\u00012\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0007\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0017H\u0007\u001a\u0010\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001fH\u0007\u001a\u0012\u0010 \u001a\u00020\u00012\b\b\u0002\u0010!\u001a\u00020\"H\u0007\u001a@\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\"2\b\b\u0002\u0010\'\u001a\u00020\"2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a\u0010\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020*H\u0002\u001a2\u0010+\u001a\u00020\u0001*\u00020,2\u0006\u0010-\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\"2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006/"}, d2 = {"FeatureCard", "", "title", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "color", "Landroidx/compose/ui/graphics/Color;", "onClick", "Lkotlin/Function0;", "FeatureCard-9LQNqLg", "(Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;JLkotlin/jvm/functions/Function0;)V", "HomeDashboard", "viewModel", "Lcom/eps/android/ui/viewmodel/DashboardViewModel;", "onNavigate", "Lkotlin/Function1;", "MainScaffold", "auditViewModel", "Lcom/eps/android/ui/viewmodel/AppAuditViewModel;", "fileViewModel", "Lcom/eps/android/ui/viewmodel/FileScanViewModel;", "networkViewModel", "Lcom/eps/android/ui/viewmodel/NetworkTrafficViewModel;", "aiViewModel", "Lcom/eps/android/ui/viewmodel/AiMentorViewModel;", "securityHubViewModel", "Lcom/eps/android/ui/viewmodel/SecurityHubViewModel;", "NetworkTrafficScreen", "SystemStatusCard", "status", "Lcom/eps/android/ui/viewmodel/DashboardViewModel$ProtectionStatus;", "VishingAlertCard", "isGeneric", "", "WizardStep", "step", "desc", "isDone", "isEnabled", "openNotificationListenerSettings", "context", "Landroid/content/Context;", "NavItem", "Landroidx/compose/foundation/layout/RowScope;", "label", "selected", "app_debug"})
public final class DashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void MainScaffold(@org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.DashboardViewModel viewModel, @org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.AppAuditViewModel auditViewModel, @org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.FileScanViewModel fileViewModel, @org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.NetworkTrafficViewModel networkViewModel, @org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.AiMentorViewModel aiViewModel, @org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.SecurityHubViewModel securityHubViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void NavItem(@org.jetbrains.annotations.NotNull
    androidx.compose.foundation.layout.RowScope $this$NavItem, @org.jetbrains.annotations.NotNull
    java.lang.String label, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.vector.ImageVector icon, boolean selected, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void HomeDashboard(@org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.DashboardViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SystemStatusCard(@org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.DashboardViewModel.ProtectionStatus status) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void WizardStep(@org.jetbrains.annotations.NotNull
    java.lang.String step, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String desc, boolean isDone, boolean isEnabled, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    private static final void openNotificationListenerSettings(android.content.Context context) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void VishingAlertCard(boolean isGeneric) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void NetworkTrafficScreen(@org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.NetworkTrafficViewModel viewModel) {
    }
}