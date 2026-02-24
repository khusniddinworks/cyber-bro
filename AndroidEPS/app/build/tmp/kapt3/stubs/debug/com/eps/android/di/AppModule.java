package com.eps.android.di;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/eps/android/di/AppModule;", "", "()V", "provideBlacklistDao", "Lcom/eps/android/data/BlacklistDao;", "db", "Lcom/eps/android/data/EpsDatabase;", "provideDatabase", "context", "Landroid/content/Context;", "provideThreatEventDao", "Lcom/eps/android/data/ThreatEventDao;", "provideThreatNotifier", "Lcom/eps/android/core/ThreatNotifier;", "provideTrustedAppDao", "Lcom/eps/android/data/TrustedAppDao;", "provideUrlScanCacheDao", "Lcom/eps/android/data/UrlScanCacheDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull
    public static final com.eps.android.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.EpsDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.ThreatEventDao provideThreatEventDao(@org.jetbrains.annotations.NotNull
    com.eps.android.data.EpsDatabase db) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.BlacklistDao provideBlacklistDao(@org.jetbrains.annotations.NotNull
    com.eps.android.data.EpsDatabase db) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.UrlScanCacheDao provideUrlScanCacheDao(@org.jetbrains.annotations.NotNull
    com.eps.android.data.EpsDatabase db) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.data.TrustedAppDao provideTrustedAppDao(@org.jetbrains.annotations.NotNull
    com.eps.android.data.EpsDatabase db) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.core.ThreatNotifier provideThreatNotifier(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
}