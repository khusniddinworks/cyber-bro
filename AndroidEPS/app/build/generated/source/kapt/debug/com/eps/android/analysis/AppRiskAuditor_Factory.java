package com.eps.android.analysis;

import com.eps.android.data.TrustedAppDao;
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
public final class AppRiskAuditor_Factory implements Factory<AppRiskAuditor> {
  private final Provider<TrustedAppDao> trustedAppDaoProvider;

  public AppRiskAuditor_Factory(Provider<TrustedAppDao> trustedAppDaoProvider) {
    this.trustedAppDaoProvider = trustedAppDaoProvider;
  }

  @Override
  public AppRiskAuditor get() {
    return newInstance(trustedAppDaoProvider.get());
  }

  public static AppRiskAuditor_Factory create(Provider<TrustedAppDao> trustedAppDaoProvider) {
    return new AppRiskAuditor_Factory(trustedAppDaoProvider);
  }

  public static AppRiskAuditor newInstance(TrustedAppDao trustedAppDao) {
    return new AppRiskAuditor(trustedAppDao);
  }
}
