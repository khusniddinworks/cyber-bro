package com.eps.android.core;

import com.eps.android.data.BlacklistDao;
import com.eps.android.data.ThreatEventDao;
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
public final class PhishingAccessibilityService_MembersInjector implements MembersInjector<PhishingAccessibilityService> {
  private final Provider<BlacklistDao> blacklistDaoProvider;

  private final Provider<ThreatEventDao> threatEventDaoProvider;

  public PhishingAccessibilityService_MembersInjector(Provider<BlacklistDao> blacklistDaoProvider,
      Provider<ThreatEventDao> threatEventDaoProvider) {
    this.blacklistDaoProvider = blacklistDaoProvider;
    this.threatEventDaoProvider = threatEventDaoProvider;
  }

  public static MembersInjector<PhishingAccessibilityService> create(
      Provider<BlacklistDao> blacklistDaoProvider,
      Provider<ThreatEventDao> threatEventDaoProvider) {
    return new PhishingAccessibilityService_MembersInjector(blacklistDaoProvider, threatEventDaoProvider);
  }

  @Override
  public void injectMembers(PhishingAccessibilityService instance) {
    injectBlacklistDao(instance, blacklistDaoProvider.get());
    injectThreatEventDao(instance, threatEventDaoProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.core.PhishingAccessibilityService.blacklistDao")
  public static void injectBlacklistDao(PhishingAccessibilityService instance,
      BlacklistDao blacklistDao) {
    instance.blacklistDao = blacklistDao;
  }

  @InjectedFieldSignature("com.eps.android.core.PhishingAccessibilityService.threatEventDao")
  public static void injectThreatEventDao(PhishingAccessibilityService instance,
      ThreatEventDao threatEventDao) {
    instance.threatEventDao = threatEventDao;
  }
}
