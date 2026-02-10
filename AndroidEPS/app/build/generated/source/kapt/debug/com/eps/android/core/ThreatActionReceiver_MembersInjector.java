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
public final class ThreatActionReceiver_MembersInjector implements MembersInjector<ThreatActionReceiver> {
  private final Provider<EventLogger> eventLoggerProvider;

  public ThreatActionReceiver_MembersInjector(Provider<EventLogger> eventLoggerProvider) {
    this.eventLoggerProvider = eventLoggerProvider;
  }

  public static MembersInjector<ThreatActionReceiver> create(
      Provider<EventLogger> eventLoggerProvider) {
    return new ThreatActionReceiver_MembersInjector(eventLoggerProvider);
  }

  @Override
  public void injectMembers(ThreatActionReceiver instance) {
    injectEventLogger(instance, eventLoggerProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.core.ThreatActionReceiver.eventLogger")
  public static void injectEventLogger(ThreatActionReceiver instance, EventLogger eventLogger) {
    instance.eventLogger = eventLogger;
  }
}
