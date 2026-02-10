package com.eps.android.ui.viewmodel;

import android.content.Context;
import com.eps.android.analysis.StaticAnalyzer;
import com.eps.android.analysis.ai.MalwareClassifier;
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
public final class FileScanViewModel_Factory implements Factory<FileScanViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<StaticAnalyzer> staticAnalyzerProvider;

  private final Provider<MalwareClassifier> malariaClassifierProvider;

  private final Provider<ThreatEventDao> threatEventDaoProvider;

  public FileScanViewModel_Factory(Provider<Context> contextProvider,
      Provider<StaticAnalyzer> staticAnalyzerProvider,
      Provider<MalwareClassifier> malariaClassifierProvider,
      Provider<ThreatEventDao> threatEventDaoProvider) {
    this.contextProvider = contextProvider;
    this.staticAnalyzerProvider = staticAnalyzerProvider;
    this.malariaClassifierProvider = malariaClassifierProvider;
    this.threatEventDaoProvider = threatEventDaoProvider;
  }

  @Override
  public FileScanViewModel get() {
    return newInstance(contextProvider.get(), staticAnalyzerProvider.get(), malariaClassifierProvider.get(), threatEventDaoProvider.get());
  }

  public static FileScanViewModel_Factory create(Provider<Context> contextProvider,
      Provider<StaticAnalyzer> staticAnalyzerProvider,
      Provider<MalwareClassifier> malariaClassifierProvider,
      Provider<ThreatEventDao> threatEventDaoProvider) {
    return new FileScanViewModel_Factory(contextProvider, staticAnalyzerProvider, malariaClassifierProvider, threatEventDaoProvider);
  }

  public static FileScanViewModel newInstance(Context context, StaticAnalyzer staticAnalyzer,
      MalwareClassifier malariaClassifier, ThreatEventDao threatEventDao) {
    return new FileScanViewModel(context, staticAnalyzer, malariaClassifier, threatEventDao);
  }
}
