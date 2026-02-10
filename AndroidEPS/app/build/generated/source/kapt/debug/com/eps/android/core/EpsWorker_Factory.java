package com.eps.android.core;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.eps.android.analysis.StaticAnalyzer;
import com.eps.android.data.BlacklistDao;
import com.eps.android.data.ThreatEventDao;
import dagger.internal.DaggerGenerated;
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
public final class EpsWorker_Factory {
  private final Provider<StaticAnalyzer> staticAnalyzerProvider;

  private final Provider<ThreatEventDao> threatEventDaoProvider;

  private final Provider<BlacklistDao> blacklistDaoProvider;

  public EpsWorker_Factory(Provider<StaticAnalyzer> staticAnalyzerProvider,
      Provider<ThreatEventDao> threatEventDaoProvider,
      Provider<BlacklistDao> blacklistDaoProvider) {
    this.staticAnalyzerProvider = staticAnalyzerProvider;
    this.threatEventDaoProvider = threatEventDaoProvider;
    this.blacklistDaoProvider = blacklistDaoProvider;
  }

  public EpsWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, staticAnalyzerProvider.get(), threatEventDaoProvider.get(), blacklistDaoProvider.get());
  }

  public static EpsWorker_Factory create(Provider<StaticAnalyzer> staticAnalyzerProvider,
      Provider<ThreatEventDao> threatEventDaoProvider,
      Provider<BlacklistDao> blacklistDaoProvider) {
    return new EpsWorker_Factory(staticAnalyzerProvider, threatEventDaoProvider, blacklistDaoProvider);
  }

  public static EpsWorker newInstance(Context appContext, WorkerParameters workerParams,
      StaticAnalyzer staticAnalyzer, ThreatEventDao threatEventDao, BlacklistDao blacklistDao) {
    return new EpsWorker(appContext, workerParams, staticAnalyzer, threatEventDao, blacklistDao);
  }
}
