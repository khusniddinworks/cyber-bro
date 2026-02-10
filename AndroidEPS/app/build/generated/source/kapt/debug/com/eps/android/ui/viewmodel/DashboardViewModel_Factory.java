package com.eps.android.ui.viewmodel;

import android.content.Context;
import com.eps.android.data.BlacklistDao;
import com.eps.android.data.ThreatEventDao;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ThreatEventDao> threatEventDaoProvider;

  private final Provider<BlacklistDao> blacklistDaoProvider;

  public DashboardViewModel_Factory(Provider<Context> contextProvider,
      Provider<ThreatEventDao> threatEventDaoProvider,
      Provider<BlacklistDao> blacklistDaoProvider) {
    this.contextProvider = contextProvider;
    this.threatEventDaoProvider = threatEventDaoProvider;
    this.blacklistDaoProvider = blacklistDaoProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(contextProvider.get(), threatEventDaoProvider.get(), blacklistDaoProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ThreatEventDao> threatEventDaoProvider,
      Provider<BlacklistDao> blacklistDaoProvider) {
    return new DashboardViewModel_Factory(contextProvider, threatEventDaoProvider, blacklistDaoProvider);
  }

  public static DashboardViewModel newInstance(Context context, ThreatEventDao threatEventDao,
      BlacklistDao blacklistDao) {
    return new DashboardViewModel(context, threatEventDao, blacklistDao);
  }
}
