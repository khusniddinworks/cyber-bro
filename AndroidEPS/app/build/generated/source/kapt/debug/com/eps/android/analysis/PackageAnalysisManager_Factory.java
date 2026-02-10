package com.eps.android.analysis;

import android.content.Context;
import com.eps.android.core.ThreatNotifier;
import com.eps.android.data.EventLogger;
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
public final class PackageAnalysisManager_Factory implements Factory<PackageAnalysisManager> {
  private final Provider<Context> contextProvider;

  private final Provider<StaticAnalyzer> staticAnalyzerProvider;

  private final Provider<ThreatNotifier> threatNotifierProvider;

  private final Provider<EventLogger> eventLoggerProvider;

  public PackageAnalysisManager_Factory(Provider<Context> contextProvider,
      Provider<StaticAnalyzer> staticAnalyzerProvider,
      Provider<ThreatNotifier> threatNotifierProvider, Provider<EventLogger> eventLoggerProvider) {
    this.contextProvider = contextProvider;
    this.staticAnalyzerProvider = staticAnalyzerProvider;
    this.threatNotifierProvider = threatNotifierProvider;
    this.eventLoggerProvider = eventLoggerProvider;
  }

  @Override
  public PackageAnalysisManager get() {
    return newInstance(contextProvider.get(), staticAnalyzerProvider.get(), threatNotifierProvider.get(), eventLoggerProvider.get());
  }

  public static PackageAnalysisManager_Factory create(Provider<Context> contextProvider,
      Provider<StaticAnalyzer> staticAnalyzerProvider,
      Provider<ThreatNotifier> threatNotifierProvider, Provider<EventLogger> eventLoggerProvider) {
    return new PackageAnalysisManager_Factory(contextProvider, staticAnalyzerProvider, threatNotifierProvider, eventLoggerProvider);
  }

  public static PackageAnalysisManager newInstance(Context context, StaticAnalyzer staticAnalyzer,
      ThreatNotifier threatNotifier, EventLogger eventLogger) {
    return new PackageAnalysisManager(context, staticAnalyzer, threatNotifier, eventLogger);
  }
}
