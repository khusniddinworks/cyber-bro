package com.eps.android.core;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class EpsWorker_AssistedFactory_Impl implements EpsWorker_AssistedFactory {
  private final EpsWorker_Factory delegateFactory;

  EpsWorker_AssistedFactory_Impl(EpsWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public EpsWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<EpsWorker_AssistedFactory> create(EpsWorker_Factory delegateFactory) {
    return InstanceFactory.create(new EpsWorker_AssistedFactory_Impl(delegateFactory));
  }
}
