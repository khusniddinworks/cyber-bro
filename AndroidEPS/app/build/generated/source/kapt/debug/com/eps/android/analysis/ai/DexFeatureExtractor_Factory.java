package com.eps.android.analysis.ai;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class DexFeatureExtractor_Factory implements Factory<DexFeatureExtractor> {
  @Override
  public DexFeatureExtractor get() {
    return newInstance();
  }

  public static DexFeatureExtractor_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DexFeatureExtractor newInstance() {
    return new DexFeatureExtractor();
  }

  private static final class InstanceHolder {
    private static final DexFeatureExtractor_Factory INSTANCE = new DexFeatureExtractor_Factory();
  }
}
