package com.eps.android.core;

import com.eps.android.data.EventLogger;
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
public final class EpsMonitoringService_MembersInjector implements MembersInjector<EpsMonitoringService> {
  private final Provider<FileMonitor> fileMonitorProvider;

  private final Provider<EventLogger> eventLoggerProvider;

  public EpsMonitoringService_MembersInjector(Provider<FileMonitor> fileMonitorProvider,
      Provider<EventLogger> eventLoggerProvider) {
    this.fileMonitorProvider = fileMonitorProvider;
    this.eventLoggerProvider = eventLoggerProvider;
  }

  public static MembersInjector<EpsMonitoringService> create(
      Provider<FileMonitor> fileMonitorProvider, Provider<EventLogger> eventLoggerProvider) {
    return new EpsMonitoringService_MembersInjector(fileMonitorProvider, eventLoggerProvider);
  }

  @Override
  public void injectMembers(EpsMonitoringService instance) {
    injectFileMonitor(instance, fileMonitorProvider.get());
    injectEventLogger(instance, eventLoggerProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.core.EpsMonitoringService.fileMonitor")
  public static void injectFileMonitor(EpsMonitoringService instance, FileMonitor fileMonitor) {
    instance.fileMonitor = fileMonitor;
  }

  @InjectedFieldSignature("com.eps.android.core.EpsMonitoringService.eventLogger")
  public static void injectEventLogger(EpsMonitoringService instance, EventLogger eventLogger) {
    instance.eventLogger = eventLogger;
  }
}
