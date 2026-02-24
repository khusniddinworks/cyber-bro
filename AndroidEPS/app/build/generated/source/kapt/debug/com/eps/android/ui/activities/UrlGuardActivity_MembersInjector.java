package com.eps.android.ui.activities;

import com.eps.android.analysis.network.UrlScanRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class UrlGuardActivity_MembersInjector implements MembersInjector<UrlGuardActivity> {
  private final Provider<UrlScanRepository> urlScanRepositoryProvider;

  public UrlGuardActivity_MembersInjector(Provider<UrlScanRepository> urlScanRepositoryProvider) {
    this.urlScanRepositoryProvider = urlScanRepositoryProvider;
  }

  public static MembersInjector<UrlGuardActivity> create(
      Provider<UrlScanRepository> urlScanRepositoryProvider) {
    return new UrlGuardActivity_MembersInjector(urlScanRepositoryProvider);
  }

  @Override
  public void injectMembers(UrlGuardActivity instance) {
    injectUrlScanRepository(instance, urlScanRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.ui.activities.UrlGuardActivity.urlScanRepository")
  public static void injectUrlScanRepository(UrlGuardActivity instance,
      UrlScanRepository urlScanRepository) {
    instance.urlScanRepository = urlScanRepository;
  }
}
