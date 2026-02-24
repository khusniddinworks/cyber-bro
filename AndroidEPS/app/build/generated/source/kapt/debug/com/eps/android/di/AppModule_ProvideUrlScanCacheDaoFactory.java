package com.eps.android.di;

import com.eps.android.data.EpsDatabase;
import com.eps.android.data.UrlScanCacheDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AppModule_ProvideUrlScanCacheDaoFactory implements Factory<UrlScanCacheDao> {
  private final Provider<EpsDatabase> dbProvider;

  public AppModule_ProvideUrlScanCacheDaoFactory(Provider<EpsDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public UrlScanCacheDao get() {
    return provideUrlScanCacheDao(dbProvider.get());
  }

  public static AppModule_ProvideUrlScanCacheDaoFactory create(Provider<EpsDatabase> dbProvider) {
    return new AppModule_ProvideUrlScanCacheDaoFactory(dbProvider);
  }

  public static UrlScanCacheDao provideUrlScanCacheDao(EpsDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideUrlScanCacheDao(db));
  }
}
