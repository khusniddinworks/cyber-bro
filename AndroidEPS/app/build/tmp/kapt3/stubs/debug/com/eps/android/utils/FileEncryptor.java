package com.eps.android.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001c\u001a\u00020\u0012H\u0002J\u001e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0016\u001a\u00020\u0017J\u0012\u0010!\u001a\u0004\u0018\u00010\u00062\u0006\u0010\"\u001a\u00020\u0014H\u0002J\u0012\u0010#\u001a\u0004\u0018\u00010\u00062\u0006\u0010\"\u001a\u00020\u0014H\u0002J\u0018\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\rH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/eps/android/utils/FileEncryptor;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "ALGORITHM", "", "IV_LENGTH_BYTE", "", "KEY_LENGTH_BIT", "PBKDF2_ITERATIONS", "SALT_LENGTH_BYTE", "SIGNATURE_V5", "", "SIGNATURE_V6", "TAG_LENGTH_BIT", "TRANSFORMATION", "decrypt", "Ljava/io/File;", "sourceUri", "Landroid/net/Uri;", "outputDir", "password", "", "deriveKey", "Ljavax/crypto/spec/SecretKeySpec;", "salt", "detectExtensionFromMagicBytes", "file", "encrypt", "", "destStream", "Ljava/io/OutputStream;", "getExtension", "uri", "getFileName", "readFully", "iStream", "Ljava/io/InputStream;", "b", "app_debug"})
public final class FileEncryptor {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String ALGORITHM = "AES";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String TRANSFORMATION = "AES/GCM/NoPadding";
    private final int TAG_LENGTH_BIT = 128;
    private final int IV_LENGTH_BYTE = 12;
    private final int SALT_LENGTH_BYTE = 16;
    private final int PBKDF2_ITERATIONS = 65536;
    private final int KEY_LENGTH_BIT = 256;
    @org.jetbrains.annotations.NotNull
    private final byte[] SIGNATURE_V5 = null;
    @org.jetbrains.annotations.NotNull
    private final byte[] SIGNATURE_V6 = null;
    
    @javax.inject.Inject
    public FileEncryptor(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Encrypts a file from sourceUri to destStream using password
     * Header Structure: [SIG 7B] [ExtLen 1B] [Ext N_Bytes] [Salt 16B] [IV 12B]
     */
    public final boolean encrypt(@org.jetbrains.annotations.NotNull
    android.net.Uri sourceUri, @org.jetbrains.annotations.NotNull
    java.io.OutputStream destStream, @org.jetbrains.annotations.NotNull
    char[] password) {
        return false;
    }
    
    /**
     * Decrypts a file. Returns the restored file if successful, null otherwise.
     */
    @org.jetbrains.annotations.Nullable
    public final java.io.File decrypt(@org.jetbrains.annotations.NotNull
    android.net.Uri sourceUri, @org.jetbrains.annotations.NotNull
    java.io.File outputDir, @org.jetbrains.annotations.NotNull
    char[] password) {
        return null;
    }
    
    private final boolean readFully(java.io.InputStream iStream, byte[] b) {
        return false;
    }
    
    private final java.lang.String detectExtensionFromMagicBytes(java.io.File file) {
        return null;
    }
    
    private final java.lang.String getFileName(android.net.Uri uri) {
        return null;
    }
    
    private final java.lang.String getExtension(android.net.Uri uri) {
        return null;
    }
    
    private final javax.crypto.spec.SecretKeySpec deriveKey(char[] password, byte[] salt) {
        return null;
    }
}