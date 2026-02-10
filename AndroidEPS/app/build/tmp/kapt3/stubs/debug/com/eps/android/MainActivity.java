package com.eps.android;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u000eH\u0002J\u0012\u0010\u0012\u001a\u00020\u000e2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u000eH\u0002J\u0012\u0010\u0016\u001a\u00020\u000e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\u0010\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u001a\u001a\u00020\u000eH\u0002J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\fH\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/eps/android/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "blacklistDao", "Lcom/eps/android/data/BlacklistDao;", "getBlacklistDao", "()Lcom/eps/android/data/BlacklistDao;", "setBlacklistDao", "(Lcom/eps/android/data/BlacklistDao;)V", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "applySavedLocale", "", "checkFirstRun", "", "checkManageExternalStorage", "handleIntent", "intent", "Landroid/content/Intent;", "markOnboardingComplete", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onNewIntent", "scheduleBackgroundTasks", "setAppLocale", "lang", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    @javax.inject.Inject
    public com.eps.android.data.BlacklistDao blacklistDao;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> requestPermissionLauncher = null;
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.BlacklistDao getBlacklistDao() {
        return null;
    }
    
    public final void setBlacklistDao(@org.jetbrains.annotations.NotNull
    com.eps.android.data.BlacklistDao p0) {
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onNewIntent(@org.jetbrains.annotations.NotNull
    android.content.Intent intent) {
    }
    
    private final void handleIntent(android.content.Intent intent) {
    }
    
    private final void applySavedLocale() {
    }
    
    private final void setAppLocale(java.lang.String lang) {
    }
    
    private final boolean checkFirstRun() {
        return false;
    }
    
    private final void markOnboardingComplete() {
    }
    
    private final void checkManageExternalStorage() {
    }
    
    private final void scheduleBackgroundTasks() {
    }
}