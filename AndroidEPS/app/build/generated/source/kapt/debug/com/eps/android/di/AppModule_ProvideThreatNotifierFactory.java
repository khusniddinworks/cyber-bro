package com.eps.android.di;

import android.content.Context;
import com.eps.android.core.ThreatNotifier;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideThreatNotifierFactory implements Factory<ThreatNotifier> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideThreatNotifierFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ThreatNotifier get() {
    return provideThreatNotifier(contextProvider.get());
  }

  public static AppModule_ProvideThreatNotifierFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideThreatNotifierFactory(contextProvider);
  }

  public static ThreatNotifier provideThreatNotifier(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideThreatNotifier(context));
  }
}
