package com.eps.android.analysis.ai;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DeviceAnalyzer_Factory implements Factory<DeviceAnalyzer> {
  private final Provider<Context> contextProvider;

  public DeviceAnalyzer_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DeviceAnalyzer get() {
    return newInstance(contextProvider.get());
  }

  public static DeviceAnalyzer_Factory create(Provider<Context> contextProvider) {
    return new DeviceAnalyzer_Factory(contextProvider);
  }

  public static DeviceAnalyzer newInstance(Context context) {
    return new DeviceAnalyzer(context);
  }
}
