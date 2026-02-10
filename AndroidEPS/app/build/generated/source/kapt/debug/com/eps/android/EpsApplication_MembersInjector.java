package com.eps.android;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class EpsApplication_MembersInjector implements MembersInjector<EpsApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public EpsApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<EpsApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new EpsApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(EpsApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.EpsApplication.workerFactory")
  public static void injectWorkerFactory(EpsApplication instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
