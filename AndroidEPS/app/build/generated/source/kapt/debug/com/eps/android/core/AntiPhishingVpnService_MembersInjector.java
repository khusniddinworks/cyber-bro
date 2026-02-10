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
public final class AntiPhishingVpnService_MembersInjector implements MembersInjector<AntiPhishingVpnService> {
  private final Provider<ThreatEventDao> threatEventDaoProvider;

  private final Provider<BlacklistDao> blacklistDaoProvider;

  public AntiPhishingVpnService_MembersInjector(Provider<ThreatEventDao> threatEventDaoProvider,
      Provider<BlacklistDao> blacklistDaoProvider) {
    this.threatEventDaoProvider = threatEventDaoProvider;
    this.blacklistDaoProvider = blacklistDaoProvider;
  }

  public static MembersInjector<AntiPhishingVpnService> create(
      Provider<ThreatEventDao> threatEventDaoProvider,
      Provider<BlacklistDao> blacklistDaoProvider) {
    return new AntiPhishingVpnService_MembersInjector(threatEventDaoProvider, blacklistDaoProvider);
  }

  @Override
  public void injectMembers(AntiPhishingVpnService instance) {
    injectThreatEventDao(instance, threatEventDaoProvider.get());
    injectBlacklistDao(instance, blacklistDaoProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.core.AntiPhishingVpnService.threatEventDao")
  public static void injectThreatEventDao(AntiPhishingVpnService instance,
      ThreatEventDao threatEventDao) {
    instance.threatEventDao = threatEventDao;
  }

  @InjectedFieldSignature("com.eps.android.core.AntiPhishingVpnService.blacklistDao")
  public static void injectBlacklistDao(AntiPhishingVpnService instance,
      BlacklistDao blacklistDao) {
    instance.blacklistDao = blacklistDao;
  }
}
