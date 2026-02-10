package com.eps.android.utils;

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
public final class FileEncryptor_Factory implements Factory<FileEncryptor> {
  private final Provider<Context> contextProvider;

  public FileEncryptor_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public FileEncryptor get() {
    return newInstance(contextProvider.get());
  }

  public static FileEncryptor_Factory create(Provider<Context> contextProvider) {
    return new FileEncryptor_Factory(contextProvider);
  }

  public static FileEncryptor newInstance(Context context) {
    return new FileEncryptor(context);
  }
}
