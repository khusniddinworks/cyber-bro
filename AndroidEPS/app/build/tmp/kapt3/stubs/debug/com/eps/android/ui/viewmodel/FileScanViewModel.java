package com.eps.android.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001&B)\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u001c\u001a\u00020\rJ\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u001e\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020 2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020 0$H\u0002J\u0006\u0010%\u001a\u00020\u001eR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\r0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/eps/android/ui/viewmodel/FileScanViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "staticAnalyzer", "Lcom/eps/android/analysis/StaticAnalyzer;", "malariaClassifier", "Lcom/eps/android/analysis/ai/MalwareClassifier;", "threatEventDao", "Lcom/eps/android/data/ThreatEventDao;", "(Landroid/content/Context;Lcom/eps/android/analysis/StaticAnalyzer;Lcom/eps/android/analysis/ai/MalwareClassifier;Lcom/eps/android/data/ThreatEventDao;)V", "_hasScanned", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_hasStoragePermission", "_isScanning", "_scannedFiles", "", "Lcom/eps/android/ui/viewmodel/FileScanViewModel$ScannedFile;", "hasScanned", "Lkotlinx/coroutines/flow/StateFlow;", "getHasScanned", "()Lkotlinx/coroutines/flow/StateFlow;", "hasStoragePermission", "getHasStoragePermission", "isScanning", "scannedFiles", "getScannedFiles", "checkStoragePermission", "deleteFile", "", "file", "Ljava/io/File;", "findApks", "dir", "list", "", "scanStorage", "ScannedFile", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class FileScanViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.StaticAnalyzer staticAnalyzer = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.ai.MalwareClassifier malariaClassifier = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.data.ThreatEventDao threatEventDao = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.eps.android.ui.viewmodel.FileScanViewModel.ScannedFile>> _scannedFiles = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.eps.android.ui.viewmodel.FileScanViewModel.ScannedFile>> scannedFiles = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isScanning = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isScanning = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _hasScanned = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> hasScanned = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _hasStoragePermission = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> hasStoragePermission = null;
    
    @javax.inject.Inject
    public FileScanViewModel(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.StaticAnalyzer staticAnalyzer, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.ai.MalwareClassifier malariaClassifier, @org.jetbrains.annotations.NotNull
    com.eps.android.data.ThreatEventDao threatEventDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.eps.android.ui.viewmodel.FileScanViewModel.ScannedFile>> getScannedFiles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isScanning() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getHasScanned() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getHasStoragePermission() {
        return null;
    }
    
    public final boolean checkStoragePermission() {
        return false;
    }
    
    public final void scanStorage() {
    }
    
    private final void findApks(java.io.File dir, java.util.List<java.io.File> list) {
    }
    
    public final void deleteFile(@org.jetbrains.annotations.NotNull
    java.io.File file) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lcom/eps/android/ui/viewmodel/FileScanViewModel$ScannedFile;", "", "file", "Ljava/io/File;", "verdict", "Lcom/eps/android/analysis/StaticAnalyzer$FileVerdict;", "aiVerdict", "Lcom/eps/android/analysis/ai/MalwareClassifier$AiVerdict;", "(Ljava/io/File;Lcom/eps/android/analysis/StaticAnalyzer$FileVerdict;Lcom/eps/android/analysis/ai/MalwareClassifier$AiVerdict;)V", "getAiVerdict", "()Lcom/eps/android/analysis/ai/MalwareClassifier$AiVerdict;", "getFile", "()Ljava/io/File;", "getVerdict", "()Lcom/eps/android/analysis/StaticAnalyzer$FileVerdict;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class ScannedFile {
        @org.jetbrains.annotations.NotNull
        private final java.io.File file = null;
        @org.jetbrains.annotations.NotNull
        private final com.eps.android.analysis.StaticAnalyzer.FileVerdict verdict = null;
        @org.jetbrains.annotations.Nullable
        private final com.eps.android.analysis.ai.MalwareClassifier.AiVerdict aiVerdict = null;
        
        public ScannedFile(@org.jetbrains.annotations.NotNull
        java.io.File file, @org.jetbrains.annotations.NotNull
        com.eps.android.analysis.StaticAnalyzer.FileVerdict verdict, @org.jetbrains.annotations.Nullable
        com.eps.android.analysis.ai.MalwareClassifier.AiVerdict aiVerdict) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.io.File getFile() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.StaticAnalyzer.FileVerdict getVerdict() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.eps.android.analysis.ai.MalwareClassifier.AiVerdict getAiVerdict() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.io.File component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.StaticAnalyzer.FileVerdict component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.eps.android.analysis.ai.MalwareClassifier.AiVerdict component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.ui.viewmodel.FileScanViewModel.ScannedFile copy(@org.jetbrains.annotations.NotNull
        java.io.File file, @org.jetbrains.annotations.NotNull
        com.eps.android.analysis.StaticAnalyzer.FileVerdict verdict, @org.jetbrains.annotations.Nullable
        com.eps.android.analysis.ai.MalwareClassifier.AiVerdict aiVerdict) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}