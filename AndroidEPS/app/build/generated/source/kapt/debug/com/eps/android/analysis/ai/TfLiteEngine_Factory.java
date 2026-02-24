package com.eps.android.analysis.ai;

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
public final class TfLiteEngine_Factory implements Factory<TfLiteEngine> {
  private final Provider<Context> contextProvider;

  public TfLiteEngine_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public TfLiteEngine get() {
    return newInstance(contextProvider.get());
  }

  public static TfLiteEngine_Factory create(Provider<Context> contextProvider) {
    return new TfLiteEngine_Factory(contextProvider);
  }

  public static TfLiteEngine newInstance(Context context) {
    return new TfLiteEngine(context);
  }
}
