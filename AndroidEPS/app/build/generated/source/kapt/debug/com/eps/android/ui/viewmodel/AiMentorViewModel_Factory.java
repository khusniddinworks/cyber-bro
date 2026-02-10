package com.eps.android.ui.viewmodel;

import android.content.Context;
import com.eps.android.analysis.ai.OfflineAiEngine;
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
public final class AiMentorViewModel_Factory implements Factory<AiMentorViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ThreatEventDao> threatEventDaoProvider;

  private final Provider<OfflineAiEngine> offlineAiProvider;

  public AiMentorViewModel_Factory(Provider<Context> contextProvider,
      Provider<ThreatEventDao> threatEventDaoProvider,
      Provider<OfflineAiEngine> offlineAiProvider) {
    this.contextProvider = contextProvider;
    this.threatEventDaoProvider = threatEventDaoProvider;
    this.offlineAiProvider = offlineAiProvider;
  }

  @Override
  public AiMentorViewModel get() {
    return newInstance(contextProvider.get(), threatEventDaoProvider.get(), offlineAiProvider.get());
  }

  public static AiMentorViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ThreatEventDao> threatEventDaoProvider,
      Provider<OfflineAiEngine> offlineAiProvider) {
    return new AiMentorViewModel_Factory(contextProvider, threatEventDaoProvider, offlineAiProvider);
  }

  public static AiMentorViewModel newInstance(Context context, ThreatEventDao threatEventDao,
      OfflineAiEngine offlineAi) {
    return new AiMentorViewModel(context, threatEventDao, offlineAi);
  }
}
