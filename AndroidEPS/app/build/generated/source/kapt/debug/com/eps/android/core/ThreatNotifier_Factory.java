package com.eps.android.core;

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
public final class ThreatNotifier_Factory implements Factory<ThreatNotifier> {
  private final Provider<Context> contextProvider;

  public ThreatNotifier_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ThreatNotifier get() {
    return newInstance(contextProvider.get());
  }

  public static ThreatNotifier_Factory create(Provider<Context> contextProvider) {
    return new ThreatNotifier_Factory(contextProvider);
  }

  public static ThreatNotifier newInstance(Context context) {
    return new ThreatNotifier(context);
  }
}
