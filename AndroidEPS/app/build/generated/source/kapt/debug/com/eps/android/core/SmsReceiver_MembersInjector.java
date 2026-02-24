package com.eps.android.core;

import com.eps.android.analysis.SmartPhishingNLP;
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
public final class SmsReceiver_MembersInjector implements MembersInjector<SmsReceiver> {
  private final Provider<SmartPhishingNLP> nlpEngineProvider;

  private final Provider<EventLogger> eventLoggerProvider;

  private final Provider<UrlScanRepository> urlScanRepositoryProvider;

  private final Provider<ThreatNotifier> threatNotifierProvider;

  public SmsReceiver_MembersInjector(Provider<SmartPhishingNLP> nlpEngineProvider,
      Provider<EventLogger> eventLoggerProvider,
      Provider<UrlScanRepository> urlScanRepositoryProvider,
      Provider<ThreatNotifier> threatNotifierProvider) {
    this.nlpEngineProvider = nlpEngineProvider;
    this.eventLoggerProvider = eventLoggerProvider;
    this.urlScanRepositoryProvider = urlScanRepositoryProvider;
    this.threatNotifierProvider = threatNotifierProvider;
  }

  public static MembersInjector<SmsReceiver> create(Provider<SmartPhishingNLP> nlpEngineProvider,
      Provider<EventLogger> eventLoggerProvider,
      Provider<UrlScanRepository> urlScanRepositoryProvider,
      Provider<ThreatNotifier> threatNotifierProvider) {
    return new SmsReceiver_MembersInjector(nlpEngineProvider, eventLoggerProvider, urlScanRepositoryProvider, threatNotifierProvider);
  }

  @Override
  public void injectMembers(SmsReceiver instance) {
    injectNlpEngine(instance, nlpEngineProvider.get());
    injectEventLogger(instance, eventLoggerProvider.get());
    injectUrlScanRepository(instance, urlScanRepositoryProvider.get());
    injectThreatNotifier(instance, threatNotifierProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.core.SmsReceiver.nlpEngine")
  public static void injectNlpEngine(SmsReceiver instance, SmartPhishingNLP nlpEngine) {
    instance.nlpEngine = nlpEngine;
  }

  @InjectedFieldSignature("com.eps.android.core.SmsReceiver.eventLogger")
  public static void injectEventLogger(SmsReceiver instance, EventLogger eventLogger) {
    instance.eventLogger = eventLogger;
  }

  @InjectedFieldSignature("com.eps.android.core.SmsReceiver.urlScanRepository")
  public static void injectUrlScanRepository(SmsReceiver instance,
      UrlScanRepository urlScanRepository) {
    instance.urlScanRepository = urlScanRepository;
  }

  @InjectedFieldSignature("com.eps.android.core.SmsReceiver.threatNotifier")
  public static void injectThreatNotifier(SmsReceiver instance, ThreatNotifier threatNotifier) {
    instance.threatNotifier = threatNotifier;
  }
}
