package com.eps.android.analysis;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
  @Override
  public AppRiskAuditor get() {
    return newInstance();
  }

  public static AppRiskAuditor_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AppRiskAuditor newInstance() {
    return new AppRiskAuditor();
  }

  private static final class InstanceHolder {
    private static final AppRiskAuditor_Factory INSTANCE = new AppRiskAuditor_Factory();
  }
}
