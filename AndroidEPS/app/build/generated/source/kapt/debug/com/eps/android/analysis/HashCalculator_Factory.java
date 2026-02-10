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
public final class HashCalculator_Factory implements Factory<HashCalculator> {
  @Override
  public HashCalculator get() {
    return newInstance();
  }

  public static HashCalculator_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static HashCalculator newInstance() {
    return new HashCalculator();
  }

  private static final class InstanceHolder {
    private static final HashCalculator_Factory INSTANCE = new HashCalculator_Factory();
  }
}
