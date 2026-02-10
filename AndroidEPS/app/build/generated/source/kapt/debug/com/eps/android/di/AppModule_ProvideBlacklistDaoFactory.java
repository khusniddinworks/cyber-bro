package com.eps.android.di;

import com.eps.android.data.BlacklistDao;
import com.eps.android.data.EpsDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AppModule_ProvideBlacklistDaoFactory implements Factory<BlacklistDao> {
  private final Provider<EpsDatabase> dbProvider;

  public AppModule_ProvideBlacklistDaoFactory(Provider<EpsDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BlacklistDao get() {
    return provideBlacklistDao(dbProvider.get());
  }

  public static AppModule_ProvideBlacklistDaoFactory create(Provider<EpsDatabase> dbProvider) {
    return new AppModule_ProvideBlacklistDaoFactory(dbProvider);
  }

  public static BlacklistDao provideBlacklistDao(EpsDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBlacklistDao(db));
  }
}
