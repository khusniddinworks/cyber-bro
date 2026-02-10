package com.eps.android.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\u001aJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0016\u0010\u001f\u001a\u00020\u000f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001e0\u000eH\u0002J\u0016\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\tJ\u000e\u0010$\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020\u000fJ$\u0010&\u001a\u00020\u001a2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u001e0\u000e2\u0006\u0010#\u001a\u00020\t2\u0006\u0010(\u001a\u00020\u000bJ\u0010\u0010)\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010*\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0006\u0010+\u001a\u00020\u001aR\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0019\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013\u00a8\u0006,"}, d2 = {"Lcom/eps/android/ui/viewmodel/FileVaultViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "encryptor", "Lcom/eps/android/utils/FileEncryptor;", "(Landroid/content/Context;Lcom/eps/android/utils/FileEncryptor;)V", "_error", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_isProcessing", "", "_resultPath", "_vaultFiles", "", "Ljava/io/File;", "error", "Lkotlinx/coroutines/flow/StateFlow;", "getError", "()Lkotlinx/coroutines/flow/StateFlow;", "isProcessing", "resultPath", "getResultPath", "vaultFiles", "getVaultFiles", "clearError", "", "clearResult", "createTempFileFromUri", "uri", "Landroid/net/Uri;", "createZipFromUris", "uris", "decryptFile", "encryptedFile", "password", "deleteFile", "file", "encryptFiles", "sourceUris", "shredOriginal", "getExtension", "getFileName", "refreshVault", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class FileVaultViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.utils.FileEncryptor encryptor = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isProcessing = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isProcessing = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _resultPath = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> resultPath = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> error = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.io.File>> _vaultFiles = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.io.File>> vaultFiles = null;
    
    @javax.inject.Inject
    public FileVaultViewModel(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.eps.android.utils.FileEncryptor encryptor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isProcessing() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getResultPath() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.io.File>> getVaultFiles() {
        return null;
    }
    
    public final void refreshVault() {
    }
    
    public final void clearResult() {
    }
    
    public final void clearError() {
    }
    
    public final void encryptFiles(@org.jetbrains.annotations.NotNull
    java.util.List<? extends android.net.Uri> sourceUris, @org.jetbrains.annotations.NotNull
    java.lang.String password, boolean shredOriginal) {
    }
    
    private final java.io.File createTempFileFromUri(android.net.Uri uri) {
        return null;
    }
    
    private final java.io.File createZipFromUris(java.util.List<? extends android.net.Uri> uris) {
        return null;
    }
    
    private final java.lang.String getFileName(android.net.Uri uri) {
        return null;
    }
    
    private final java.lang.String getExtension(android.net.Uri uri) {
        return null;
    }
    
    public final void decryptFile(@org.jetbrains.annotations.NotNull
    java.io.File encryptedFile, @org.jetbrains.annotations.NotNull
    java.lang.String password) {
    }
    
    public final void deleteFile(@org.jetbrains.annotations.NotNull
    java.io.File file) {
    }
}