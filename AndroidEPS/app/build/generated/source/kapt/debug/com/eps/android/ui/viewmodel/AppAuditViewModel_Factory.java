package com.eps.android.ui.viewmodel;

import android.content.Context;
import com.eps.android.analysis.AppRiskAuditor;
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
public final class AppAuditViewModel_Factory implements Factory<AppAuditViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<AppRiskAuditor> auditorProvider;

  private final Provider<ThreatEventDao> threatEventDaoProvider;

  public AppAuditViewModel_Factory(Provider<Context> contextProvider,
      Provider<AppRiskAuditor> auditorProvider, Provider<ThreatEventDao> threatEventDaoProvider) {
    this.contextProvider = contextProvider;
    this.auditorProvider = auditorProvider;
    this.threatEventDaoProvider = threatEventDaoProvider;
  }

  @Override
  public AppAuditViewModel get() {
    return newInstance(contextProvider.get(), auditorProvider.get(), threatEventDaoProvider.get());
  }

  public static AppAuditViewModel_Factory create(Provider<Context> contextProvider,
      Provider<AppRiskAuditor> auditorProvider, Provider<ThreatEventDao> threatEventDaoProvider) {
    return new AppAuditViewModel_Factory(contextProvider, auditorProvider, threatEventDaoProvider);
  }

  public static AppAuditViewModel newInstance(Context context, AppRiskAuditor auditor,
      ThreatEventDao threatEventDao) {
    return new AppAuditViewModel(context, auditor, threatEventDao);
  }
}
