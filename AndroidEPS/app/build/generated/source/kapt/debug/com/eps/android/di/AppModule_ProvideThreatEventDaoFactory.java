package com.eps.android.di;

import com.eps.android.data.EpsDatabase;
import com.eps.android.data.ThreatEventDao;
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
public final class AppModule_ProvideThreatEventDaoFactory implements Factory<ThreatEventDao> {
  private final Provider<EpsDatabase> dbProvider;

  public AppModule_ProvideThreatEventDaoFactory(Provider<EpsDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ThreatEventDao get() {
    return provideThreatEventDao(dbProvider.get());
  }

  public static AppModule_ProvideThreatEventDaoFactory create(Provider<EpsDatabase> dbProvider) {
    return new AppModule_ProvideThreatEventDaoFactory(dbProvider);
  }

  public static ThreatEventDao provideThreatEventDao(EpsDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideThreatEventDao(db));
  }
}
