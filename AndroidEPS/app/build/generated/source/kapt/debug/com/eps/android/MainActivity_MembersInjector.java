package com.eps.android;

import com.eps.android.data.BlacklistDao;
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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<BlacklistDao> blacklistDaoProvider;

  public MainActivity_MembersInjector(Provider<BlacklistDao> blacklistDaoProvider) {
    this.blacklistDaoProvider = blacklistDaoProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<BlacklistDao> blacklistDaoProvider) {
    return new MainActivity_MembersInjector(blacklistDaoProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectBlacklistDao(instance, blacklistDaoProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.MainActivity.blacklistDao")
  public static void injectBlacklistDao(MainActivity instance, BlacklistDao blacklistDao) {
    instance.blacklistDao = blacklistDao;
  }
}
