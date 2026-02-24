package com.eps.android.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a0\u0010\u0007\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u001a2\u0010\u000e\u001a\u00020\u0001*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0016H\u0007\u00a8\u0006\u0017"}, d2 = {"HomeDashboard", "", "viewModel", "Lcom/eps/android/ui/viewmodel/DashboardViewModel;", "onNavigate", "Lkotlin/Function1;", "", "MainScaffold", "fileViewModel", "Lcom/eps/android/ui/viewmodel/FileScanViewModel;", "aiViewModel", "Lcom/eps/android/ui/viewmodel/AiMentorViewModel;", "securityHubViewModel", "Lcom/eps/android/ui/viewmodel/SecurityHubViewModel;", "NavItem", "Landroidx/compose/foundation/layout/RowScope;", "label", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "selected", "", "onClick", "Lkotlin/Function0;", "app_debug"})
public final class DashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void MainScaffold(@org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.DashboardViewModel viewModel, @org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.FileScanViewModel fileViewModel, @org.jetbrains.annotations.NotNull
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
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void HomeDashboard(@org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.DashboardViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate) {
    }
}