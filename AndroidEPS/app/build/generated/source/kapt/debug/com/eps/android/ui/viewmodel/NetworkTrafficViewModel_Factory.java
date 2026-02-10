package com.eps.android.ui.viewmodel;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class NetworkTrafficViewModel_Factory implements Factory<NetworkTrafficViewModel> {
  private final Provider<Context> contextProvider;

  public NetworkTrafficViewModel_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NetworkTrafficViewModel get() {
    return newInstance(contextProvider.get());
  }

  public static NetworkTrafficViewModel_Factory create(Provider<Context> contextProvider) {
    return new NetworkTrafficViewModel_Factory(contextProvider);
  }

  public static NetworkTrafficViewModel newInstance(Context context) {
    return new NetworkTrafficViewModel(context);
  }
}
