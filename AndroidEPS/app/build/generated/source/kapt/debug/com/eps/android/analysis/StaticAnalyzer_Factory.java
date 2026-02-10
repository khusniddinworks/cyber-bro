package com.eps.android.analysis;

import android.content.Context;
import com.eps.android.analysis.ai.MalwareClassifier;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class StaticAnalyzer_Factory implements Factory<StaticAnalyzer> {
  private final Provider<Context> contextProvider;

  private final Provider<HashCalculator> hashCalculatorProvider;

  private final Provider<EntropyCalculator> entropyCalculatorProvider;

  private final Provider<ApkAnalyzer> apkAnalyzerProvider;

  private final Provider<MalwareClassifier> malwareClassifierProvider;

  public StaticAnalyzer_Factory(Provider<Context> contextProvider,
      Provider<HashCalculator> hashCalculatorProvider,
      Provider<EntropyCalculator> entropyCalculatorProvider,
      Provider<ApkAnalyzer> apkAnalyzerProvider,
      Provider<MalwareClassifier> malwareClassifierProvider) {
    this.contextProvider = contextProvider;
    this.hashCalculatorProvider = hashCalculatorProvider;
    this.entropyCalculatorProvider = entropyCalculatorProvider;
    this.apkAnalyzerProvider = apkAnalyzerProvider;
    this.malwareClassifierProvider = malwareClassifierProvider;
  }

  @Override
  public StaticAnalyzer get() {
    return newInstance(contextProvider.get(), hashCalculatorProvider.get(), entropyCalculatorProvider.get(), apkAnalyzerProvider.get(), malwareClassifierProvider.get());
  }

  public static StaticAnalyzer_Factory create(Provider<Context> contextProvider,
      Provider<HashCalculator> hashCalculatorProvider,
      Provider<EntropyCalculator> entropyCalculatorProvider,
      Provider<ApkAnalyzer> apkAnalyzerProvider,
      Provider<MalwareClassifier> malwareClassifierProvider) {
    return new StaticAnalyzer_Factory(contextProvider, hashCalculatorProvider, entropyCalculatorProvider, apkAnalyzerProvider, malwareClassifierProvider);
  }

  public static StaticAnalyzer newInstance(Context context, HashCalculator hashCalculator,
      EntropyCalculator entropyCalculator, ApkAnalyzer apkAnalyzer,
      MalwareClassifier malwareClassifier) {
    return new StaticAnalyzer(context, hashCalculator, entropyCalculator, apkAnalyzer, malwareClassifier);
  }
}
