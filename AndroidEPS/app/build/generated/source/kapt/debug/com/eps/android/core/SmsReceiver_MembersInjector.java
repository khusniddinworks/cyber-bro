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
public final class SmsReceiver_MembersInjector implements MembersInjector<SmsReceiver> {
  private final Provider<EventLogger> eventLoggerProvider;

  public SmsReceiver_MembersInjector(Provider<EventLogger> eventLoggerProvider) {
    this.eventLoggerProvider = eventLoggerProvider;
  }

  public static MembersInjector<SmsReceiver> create(Provider<EventLogger> eventLoggerProvider) {
    return new SmsReceiver_MembersInjector(eventLoggerProvider);
  }

  @Override
  public void injectMembers(SmsReceiver instance) {
    injectEventLogger(instance, eventLoggerProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.core.SmsReceiver.eventLogger")
  public static void injectEventLogger(SmsReceiver instance, EventLogger eventLogger) {
    instance.eventLogger = eventLogger;
  }
}
