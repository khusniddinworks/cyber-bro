package com.eps.android.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aZ\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\bH\u0007\u001a\u0012\u0010\u000e\u001a\u00020\u00012\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007\u001a,\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a.\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u00a8\u0006\u001c"}, d2 = {"EncryptionForm", "", "selectedUris", "", "Landroid/net/Uri;", "isProcessing", "", "error", "", "onCancel", "Lkotlin/Function0;", "onEncrypt", "Lkotlin/Function2;", "resultPath", "FileVaultScreen", "viewModel", "Lcom/eps/android/ui/viewmodel/FileVaultViewModel;", "VaultFileItem", "file", "Ljava/io/File;", "onDecrypt", "onDelete", "VaultTabButton", "text", "isSelected", "modifier", "Landroidx/compose/ui/Modifier;", "onClick", "app_debug"})
public final class FileVaultScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void FileVaultScreen(@org.jetbrains.annotations.NotNull
    com.eps.android.ui.viewmodel.FileVaultViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void VaultFileItem(@org.jetbrains.annotations.NotNull
    java.io.File file, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDecrypt, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void EncryptionForm(@org.jetbrains.annotations.NotNull
    java.util.List<? extends android.net.Uri> selectedUris, boolean isProcessing, @org.jetbrains.annotations.Nullable
    java.lang.String error, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onEncrypt, @org.jetbrains.annotations.Nullable
    java.lang.String resultPath) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void VaultTabButton(@org.jetbrains.annotations.NotNull
    java.lang.String text, boolean isSelected, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}