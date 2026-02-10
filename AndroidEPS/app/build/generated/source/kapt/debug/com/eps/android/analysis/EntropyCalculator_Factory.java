package com.eps.android.analysis;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class EntropyCalculator_Factory implements Factory<EntropyCalculator> {
  @Override
  public EntropyCalculator get() {
    return newInstance();
  }

  public static EntropyCalculator_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static EntropyCalculator newInstance() {
    return new EntropyCalculator();
  }

  private static final class InstanceHolder {
    private static final EntropyCalculator_Factory INSTANCE = new EntropyCalculator_Factory();
  }
}
