package com.eps.android.core;

import com.eps.android.analysis.StaticAnalyzer;
import com.eps.android.data.EventLogger;
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
public final class FileMonitor_Factory implements Factory<FileMonitor> {
  private final Provider<StaticAnalyzer> staticAnalyzerProvider;

  private final Provider<EventLogger> eventLoggerProvider;

  private final Provider<ThreatNotifier> threatNotifierProvider;

  public FileMonitor_Factory(Provider<StaticAnalyzer> staticAnalyzerProvider,
      Provider<EventLogger> eventLoggerProvider, Provider<ThreatNotifier> threatNotifierProvider) {
    this.staticAnalyzerProvider = staticAnalyzerProvider;
    this.eventLoggerProvider = eventLoggerProvider;
    this.threatNotifierProvider = threatNotifierProvider;
  }

  @Override
  public FileMonitor get() {
    return newInstance(staticAnalyzerProvider.get(), eventLoggerProvider.get(), threatNotifierProvider.get());
  }

  public static FileMonitor_Factory create(Provider<StaticAnalyzer> staticAnalyzerProvider,
      Provider<EventLogger> eventLoggerProvider, Provider<ThreatNotifier> threatNotifierProvider) {
    return new FileMonitor_Factory(staticAnalyzerProvider, eventLoggerProvider, threatNotifierProvider);
  }

  public static FileMonitor newInstance(StaticAnalyzer staticAnalyzer, EventLogger eventLogger,
      ThreatNotifier threatNotifier) {
    return new FileMonitor(staticAnalyzer, eventLogger, threatNotifier);
  }
}
