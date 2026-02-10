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
public final class OfflineAiEngine_Factory implements Factory<OfflineAiEngine> {
  private final Provider<Context> contextProvider;

  private final Provider<DeviceAnalyzer> deviceAnalyzerProvider;

  public OfflineAiEngine_Factory(Provider<Context> contextProvider,
      Provider<DeviceAnalyzer> deviceAnalyzerProvider) {
    this.contextProvider = contextProvider;
    this.deviceAnalyzerProvider = deviceAnalyzerProvider;
  }

  @Override
  public OfflineAiEngine get() {
    return newInstance(contextProvider.get(), deviceAnalyzerProvider.get());
  }

  public static OfflineAiEngine_Factory create(Provider<Context> contextProvider,
      Provider<DeviceAnalyzer> deviceAnalyzerProvider) {
    return new OfflineAiEngine_Factory(contextProvider, deviceAnalyzerProvider);
  }

  public static OfflineAiEngine newInstance(Context context, DeviceAnalyzer deviceAnalyzer) {
    return new OfflineAiEngine(context, deviceAnalyzer);
  }
}
