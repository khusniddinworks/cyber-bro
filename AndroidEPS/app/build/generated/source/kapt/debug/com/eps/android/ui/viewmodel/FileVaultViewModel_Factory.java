package com.eps.android.ui.viewmodel;

import android.content.Context;
import com.eps.android.utils.FileEncryptor;
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
public final class FileVaultViewModel_Factory implements Factory<FileVaultViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<FileEncryptor> encryptorProvider;

  public FileVaultViewModel_Factory(Provider<Context> contextProvider,
      Provider<FileEncryptor> encryptorProvider) {
    this.contextProvider = contextProvider;
    this.encryptorProvider = encryptorProvider;
  }

  @Override
  public FileVaultViewModel get() {
    return newInstance(contextProvider.get(), encryptorProvider.get());
  }

  public static FileVaultViewModel_Factory create(Provider<Context> contextProvider,
      Provider<FileEncryptor> encryptorProvider) {
    return new FileVaultViewModel_Factory(contextProvider, encryptorProvider);
  }

  public static FileVaultViewModel newInstance(Context context, FileEncryptor encryptor) {
    return new FileVaultViewModel(context, encryptor);
  }
}
