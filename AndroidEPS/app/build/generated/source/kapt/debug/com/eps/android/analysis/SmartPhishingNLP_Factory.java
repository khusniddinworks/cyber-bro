package com.eps.android.analysis;

import com.eps.android.analysis.ai.TfLiteEngine;
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
public final class SmartPhishingNLP_Factory implements Factory<SmartPhishingNLP> {
  private final Provider<TfLiteEngine> tfLiteEngineProvider;

  public SmartPhishingNLP_Factory(Provider<TfLiteEngine> tfLiteEngineProvider) {
    this.tfLiteEngineProvider = tfLiteEngineProvider;
  }

  @Override
  public SmartPhishingNLP get() {
    return newInstance(tfLiteEngineProvider.get());
  }

  public static SmartPhishingNLP_Factory create(Provider<TfLiteEngine> tfLiteEngineProvider) {
    return new SmartPhishingNLP_Factory(tfLiteEngineProvider);
  }

  public static SmartPhishingNLP newInstance(TfLiteEngine tfLiteEngine) {
    return new SmartPhishingNLP(tfLiteEngine);
  }
}
