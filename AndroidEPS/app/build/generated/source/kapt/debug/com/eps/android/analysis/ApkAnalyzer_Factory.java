package com.eps.android.analysis;

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
public final class ApkAnalyzer_Factory implements Factory<ApkAnalyzer> {
  private final Provider<AppRiskAuditor> auditorProvider;

  public ApkAnalyzer_Factory(Provider<AppRiskAuditor> auditorProvider) {
    this.auditorProvider = auditorProvider;
  }

  @Override
  public ApkAnalyzer get() {
    return newInstance(auditorProvider.get());
  }

  public static ApkAnalyzer_Factory create(Provider<AppRiskAuditor> auditorProvider) {
    return new ApkAnalyzer_Factory(auditorProvider);
  }

  public static ApkAnalyzer newInstance(AppRiskAuditor auditor) {
    return new ApkAnalyzer(auditor);
  }
}
