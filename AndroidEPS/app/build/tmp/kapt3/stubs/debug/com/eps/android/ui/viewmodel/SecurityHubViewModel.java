package com.eps.android.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001:\u0002\u0011\u0012B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0013"}, d2 = {"Lcom/eps/android/ui/viewmodel/SecurityHubViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_stats", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/eps/android/ui/viewmodel/SecurityHubViewModel$SecurityStats;", "stats", "Lkotlinx/coroutines/flow/StateFlow;", "getStats", "()Lkotlinx/coroutines/flow/StateFlow;", "openDeveloperSettings", "", "openNotificationSettings", "openSecuritySettings", "scanSecurityState", "AppInfo", "SecurityStats", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class SecurityHubViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.eps.android.ui.viewmodel.SecurityHubViewModel.SecurityStats> _stats = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.eps.android.ui.viewmodel.SecurityHubViewModel.SecurityStats> stats = null;
    
    @javax.inject.Inject
    public SecurityHubViewModel(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.eps.android.ui.viewmodel.SecurityHubViewModel.SecurityStats> getStats() {
        return null;
    }
    
    public final void scanSecurityState() {
    }
    
    public final void openDeveloperSettings() {
    }
    
    public final void openSecuritySettings() {
    }
    
    public final void openNotificationSettings() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J)\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lcom/eps/android/ui/viewmodel/SecurityHubViewModel$AppInfo;", "", "name", "", "packageName", "icon", "Landroid/graphics/drawable/Drawable;", "(Ljava/lang/String;Ljava/lang/String;Landroid/graphics/drawable/Drawable;)V", "getIcon", "()Landroid/graphics/drawable/Drawable;", "getName", "()Ljava/lang/String;", "getPackageName", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class AppInfo {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String packageName = null;
        @org.jetbrains.annotations.Nullable
        private final android.graphics.drawable.Drawable icon = null;
        
        public AppInfo(@org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String packageName, @org.jetbrains.annotations.Nullable
        android.graphics.drawable.Drawable icon) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPackageName() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final android.graphics.drawable.Drawable getIcon() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final android.graphics.drawable.Drawable component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo copy(@org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String packageName, @org.jetbrains.annotations.Nullable
        android.graphics.drawable.Drawable icon) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0015\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B]\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u00a2\u0006\u0002\u0010\fJ\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0017\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\bH\u00c6\u0003Ja\u0010\u001a\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0011R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011\u00a8\u0006!"}, d2 = {"Lcom/eps/android/ui/viewmodel/SecurityHubViewModel$SecurityStats;", "", "appsWithCamera", "", "Lcom/eps/android/ui/viewmodel/SecurityHubViewModel$AppInfo;", "appsWithMic", "appsWithLocation", "isDevModeEnabled", "", "isAdbEnabled", "isUnknownSourcesAllowed", "screenLockSecure", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;ZZZZ)V", "getAppsWithCamera", "()Ljava/util/List;", "getAppsWithLocation", "getAppsWithMic", "()Z", "getScreenLockSecure", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class SecurityStats {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> appsWithCamera = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> appsWithMic = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> appsWithLocation = null;
        private final boolean isDevModeEnabled = false;
        private final boolean isAdbEnabled = false;
        private final boolean isUnknownSourcesAllowed = false;
        private final boolean screenLockSecure = false;
        
        public SecurityStats(@org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> appsWithCamera, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> appsWithMic, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> appsWithLocation, boolean isDevModeEnabled, boolean isAdbEnabled, boolean isUnknownSourcesAllowed, boolean screenLockSecure) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> getAppsWithCamera() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> getAppsWithMic() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> getAppsWithLocation() {
            return null;
        }
        
        public final boolean isDevModeEnabled() {
            return false;
        }
        
        public final boolean isAdbEnabled() {
            return false;
        }
        
        public final boolean isUnknownSourcesAllowed() {
            return false;
        }
        
        public final boolean getScreenLockSecure() {
            return false;
        }
        
        public SecurityStats() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> component3() {
            return null;
        }
        
        public final boolean component4() {
            return false;
        }
        
        public final boolean component5() {
            return false;
        }
        
        public final boolean component6() {
            return false;
        }
        
        public final boolean component7() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.ui.viewmodel.SecurityHubViewModel.SecurityStats copy(@org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> appsWithCamera, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> appsWithMic, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.ui.viewmodel.SecurityHubViewModel.AppInfo> appsWithLocation, boolean isDevModeEnabled, boolean isAdbEnabled, boolean isUnknownSourcesAllowed, boolean screenLockSecure) {
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