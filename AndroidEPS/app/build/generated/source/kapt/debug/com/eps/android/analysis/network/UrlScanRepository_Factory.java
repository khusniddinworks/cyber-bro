package com.eps.android.analysis.network;

import com.eps.android.data.UrlScanCacheDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class UrlScanRepository_Factory implements Factory<UrlScanRepository> {
  private final Provider<UrlScanCacheDao> urlScanCacheDaoProvider;

  public UrlScanRepository_Factory(Provider<UrlScanCacheDao> urlScanCacheDaoProvider) {
    this.urlScanCacheDaoProvider = urlScanCacheDaoProvider;
  }

  @Override
  public UrlScanRepository get() {
    return newInstance(urlScanCacheDaoProvider.get());
  }

  public static UrlScanRepository_Factory create(
      Provider<UrlScanCacheDao> urlScanCacheDaoProvider) {
    return new UrlScanRepository_Factory(urlScanCacheDaoProvider);
  }

  public static UrlScanRepository newInstance(UrlScanCacheDao urlScanCacheDao) {
    return new UrlScanRepository(urlScanCacheDao);
  }
}
