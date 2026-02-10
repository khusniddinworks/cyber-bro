package com.eps.android.data;

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
public final class EventLogger_Factory implements Factory<EventLogger> {
  private final Provider<ThreatEventDao> threatEventDaoProvider;

  public EventLogger_Factory(Provider<ThreatEventDao> threatEventDaoProvider) {
    this.threatEventDaoProvider = threatEventDaoProvider;
  }

  @Override
  public EventLogger get() {
    return newInstance(threatEventDaoProvider.get());
  }

  public static EventLogger_Factory create(Provider<ThreatEventDao> threatEventDaoProvider) {
    return new EventLogger_Factory(threatEventDaoProvider);
  }

  public static EventLogger newInstance(ThreatEventDao threatEventDao) {
    return new EventLogger(threatEventDao);
  }
}
