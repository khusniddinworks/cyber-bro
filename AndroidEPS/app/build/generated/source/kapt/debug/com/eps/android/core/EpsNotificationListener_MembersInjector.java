package com.eps.android.core;

import com.eps.android.analysis.network.UrlScanRepository;
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
public final class EpsNotificationListener_MembersInjector implements MembersInjector<EpsNotificationListener> {
  private final Provider<UrlScanRepository> urlScanRepositoryProvider;

  private final Provider<EventLogger> eventLoggerProvider;

  private final Provider<ThreatNotifier> threatNotifierProvider;

  public EpsNotificationListener_MembersInjector(
      Provider<UrlScanRepository> urlScanRepositoryProvider,
      Provider<EventLogger> eventLoggerProvider, Provider<ThreatNotifier> threatNotifierProvider) {
    this.urlScanRepositoryProvider = urlScanRepositoryProvider;
    this.eventLoggerProvider = eventLoggerProvider;
    this.threatNotifierProvider = threatNotifierProvider;
  }

  public static MembersInjector<EpsNotificationListener> create(
      Provider<UrlScanRepository> urlScanRepositoryProvider,
      Provider<EventLogger> eventLoggerProvider, Provider<ThreatNotifier> threatNotifierProvider) {
    return new EpsNotificationListener_MembersInjector(urlScanRepositoryProvider, eventLoggerProvider, threatNotifierProvider);
  }

  @Override
  public void injectMembers(EpsNotificationListener instance) {
    injectUrlScanRepository(instance, urlScanRepositoryProvider.get());
    injectEventLogger(instance, eventLoggerProvider.get());
    injectThreatNotifier(instance, threatNotifierProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.core.EpsNotificationListener.urlScanRepository")
  public static void injectUrlScanRepository(EpsNotificationListener instance,
      UrlScanRepository urlScanRepository) {
    instance.urlScanRepository = urlScanRepository;
  }

  @InjectedFieldSignature("com.eps.android.core.EpsNotificationListener.eventLogger")
  public static void injectEventLogger(EpsNotificationListener instance, EventLogger eventLogger) {
    instance.eventLogger = eventLogger;
  }

  @InjectedFieldSignature("com.eps.android.core.EpsNotificationListener.threatNotifier")
  public static void injectThreatNotifier(EpsNotificationListener instance,
      ThreatNotifier threatNotifier) {
    instance.threatNotifier = threatNotifier;
  }
}
