package com.eps.android.core;

import com.eps.android.analysis.PackageAnalysisManager;
import com.eps.android.data.EventLogger;
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
public final class PackageEventReceiver_MembersInjector implements MembersInjector<PackageEventReceiver> {
  private final Provider<PackageAnalysisManager> packageAnalysisManagerProvider;

  private final Provider<EventLogger> eventLoggerProvider;

  public PackageEventReceiver_MembersInjector(
      Provider<PackageAnalysisManager> packageAnalysisManagerProvider,
      Provider<EventLogger> eventLoggerProvider) {
    this.packageAnalysisManagerProvider = packageAnalysisManagerProvider;
    this.eventLoggerProvider = eventLoggerProvider;
  }

  public static MembersInjector<PackageEventReceiver> create(
      Provider<PackageAnalysisManager> packageAnalysisManagerProvider,
      Provider<EventLogger> eventLoggerProvider) {
    return new PackageEventReceiver_MembersInjector(packageAnalysisManagerProvider, eventLoggerProvider);
  }

  @Override
  public void injectMembers(PackageEventReceiver instance) {
    injectPackageAnalysisManager(instance, packageAnalysisManagerProvider.get());
    injectEventLogger(instance, eventLoggerProvider.get());
  }

  @InjectedFieldSignature("com.eps.android.core.PackageEventReceiver.packageAnalysisManager")
  public static void injectPackageAnalysisManager(PackageEventReceiver instance,
      PackageAnalysisManager packageAnalysisManager) {
    instance.packageAnalysisManager = packageAnalysisManager;
  }

  @InjectedFieldSignature("com.eps.android.core.PackageEventReceiver.eventLogger")
  public static void injectEventLogger(PackageEventReceiver instance, EventLogger eventLogger) {
    instance.eventLogger = eventLogger;
  }
}
