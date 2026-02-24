package com.eps.android.analysis.ai;

/**
 * Offline AI Engine PRO - Rule-Based Context-Aware System
 * 100% Offline, no external dependencies
 * Qurilmani 100% tahlil qiladi va foydalanuvchiga tushunarli tilda ma'lumot beradi
 */
@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001:\u0005./012B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u001e\u0010\u0013\u001a\u00020\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u001e\u0010\u0015\u001a\u00020\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J(\u0010\u0016\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0017\u001a\u00020\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0010\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0018\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0010\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u0010\u0010!\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J0\u0010\"\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\b\u0010%\u001a\u0004\u0018\u00010\nH\u0002J&\u0010&\u001a\u00020\'2\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J&\u0010(\u001a\u00020\'2\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J&\u0010)\u001a\u00020\'2\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J$\u0010+\u001a\u00020\'2\u0006\u0010,\u001a\u00020\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010-\u001a\u00020\nR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00063"}, d2 = {"Lcom/eps/android/analysis/ai/OfflineAiEngine;", "", "context", "Landroid/content/Context;", "deviceAnalyzer", "Lcom/eps/android/analysis/ai/DeviceAnalyzer;", "(Landroid/content/Context;Lcom/eps/android/analysis/ai/DeviceAnalyzer;)V", "cachedFullReport", "Lcom/eps/android/analysis/ai/DeviceAnalyzer$FullDeviceReport;", "lastDeviceStats", "Lcom/eps/android/analysis/ai/OfflineAiEngine$DeviceStatus;", "analyzeVishing", "Lcom/eps/android/analysis/ai/OfflineAiEngine$VishingAnalysisResult;", "text", "", "buildExtendedContext", "threats", "", "Lcom/eps/android/data/ThreatEvent;", "buildMasterNarrative", "lang", "buildThreatReport", "checkSystemActivity", "q", "detectAction", "Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemAction;", "explainBackgroundProcesses", "explainBatteryStatus", "explainEvent", "event", "explainNetworkStatus", "explainResourceUsage", "explainStorageStatus", "explainSuspiciousApps", "generateSystemNarrative", "criticalCount", "", "stats", "handleEnglishMasterResponse", "Lcom/eps/android/analysis/ai/OfflineAiEngine$AiResponse;", "handleRussianMasterResponse", "handleUzbekMasterResponse", "performFullDeviceAnalysis", "respond", "query", "deviceStats", "AiResponse", "DeviceStatus", "SystemAction", "SystemActionType", "VishingAnalysisResult", "app_debug"})
public final class OfflineAiEngine {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.ai.DeviceAnalyzer deviceAnalyzer = null;
    @org.jetbrains.annotations.Nullable
    private com.eps.android.analysis.ai.OfflineAiEngine.DeviceStatus lastDeviceStats;
    @org.jetbrains.annotations.Nullable
    private com.eps.android.analysis.ai.DeviceAnalyzer.FullDeviceReport cachedFullReport;
    
    @javax.inject.Inject
    public OfflineAiEngine(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.ai.DeviceAnalyzer deviceAnalyzer) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.ai.OfflineAiEngine.AiResponse respond(@org.jetbrains.annotations.NotNull
    java.lang.String query, @org.jetbrains.annotations.NotNull
    java.util.List<com.eps.android.data.ThreatEvent> threats, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.ai.OfflineAiEngine.DeviceStatus deviceStats) {
        return null;
    }
    
    private final java.lang.String checkSystemActivity(java.lang.String q, java.util.List<com.eps.android.data.ThreatEvent> threats, java.lang.String lang) {
        return null;
    }
    
    private final java.lang.String buildMasterNarrative(java.util.List<com.eps.android.data.ThreatEvent> threats, java.lang.String lang) {
        return null;
    }
    
    private final java.lang.String explainEvent(com.eps.android.data.ThreatEvent event, java.lang.String lang) {
        return null;
    }
    
    private final com.eps.android.analysis.ai.OfflineAiEngine.AiResponse handleUzbekMasterResponse(java.lang.String q, int criticalCount, java.util.List<com.eps.android.data.ThreatEvent> threats) {
        return null;
    }
    
    private final com.eps.android.analysis.ai.OfflineAiEngine.AiResponse handleRussianMasterResponse(java.lang.String q, int criticalCount, java.util.List<com.eps.android.data.ThreatEvent> threats) {
        return null;
    }
    
    private final java.lang.String generateSystemNarrative(java.lang.String lang, int criticalCount, java.util.List<com.eps.android.data.ThreatEvent> threats, com.eps.android.analysis.ai.OfflineAiEngine.DeviceStatus stats) {
        return null;
    }
    
    private final com.eps.android.analysis.ai.OfflineAiEngine.AiResponse handleEnglishMasterResponse(java.lang.String q, int criticalCount, java.util.List<com.eps.android.data.ThreatEvent> threats) {
        return null;
    }
    
    private final com.eps.android.analysis.ai.OfflineAiEngine.SystemAction detectAction(java.lang.String q, java.lang.String lang) {
        return null;
    }
    
    private final java.lang.String buildExtendedContext(java.util.List<com.eps.android.data.ThreatEvent> threats) {
        return null;
    }
    
    private final java.lang.String buildThreatReport(java.util.List<com.eps.android.data.ThreatEvent> threats, java.lang.String lang) {
        return null;
    }
    
    /**
     * Vishing (Voice Phishing) Analysis
     */
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.ai.OfflineAiEngine.VishingAnalysisResult analyzeVishing(@org.jetbrains.annotations.NotNull
    java.lang.String text) {
        return null;
    }
    
    /**
     * To'liq qurilma tahlili - foydalanuvchiga tushunarli tilda
     */
    private final java.lang.String performFullDeviceAnalysis(java.lang.String lang) {
        return null;
    }
    
    /**
     * Background jarayonlarni tushuntirish
     */
    private final java.lang.String explainBackgroundProcesses(java.lang.String lang) {
        return null;
    }
    
    /**
     * Shubhali ilovalarni tushuntirish
     */
    private final java.lang.String explainSuspiciousApps(java.lang.String lang) {
        return null;
    }
    
    /**
     * Resurs ishlatilishini tushuntirish
     */
    private final java.lang.String explainResourceUsage(java.lang.String lang) {
        return null;
    }
    
    /**
     * Tarmoq holatini tushuntirish
     */
    private final java.lang.String explainNetworkStatus(java.lang.String lang) {
        return null;
    }
    
    /**
     * Batareya holatini tushuntirish
     */
    private final java.lang.String explainBatteryStatus(java.lang.String lang) {
        return null;
    }
    
    /**
     * Xotira holatini tushuntirish
     */
    private final java.lang.String explainStorageStatus(java.lang.String lang) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/eps/android/analysis/ai/OfflineAiEngine$AiResponse;", "", "text", "", "action", "Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemAction;", "(Ljava/lang/String;Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemAction;)V", "getAction", "()Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemAction;", "getText", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class AiResponse {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String text = null;
        @org.jetbrains.annotations.NotNull
        private final com.eps.android.analysis.ai.OfflineAiEngine.SystemAction action = null;
        
        public AiResponse(@org.jetbrains.annotations.NotNull
        java.lang.String text, @org.jetbrains.annotations.NotNull
        com.eps.android.analysis.ai.OfflineAiEngine.SystemAction action) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getText() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.OfflineAiEngine.SystemAction getAction() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.OfflineAiEngine.SystemAction component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.OfflineAiEngine.AiResponse copy(@org.jetbrains.annotations.NotNull
        java.lang.String text, @org.jetbrains.annotations.NotNull
        com.eps.android.analysis.ai.OfflineAiEngine.SystemAction action) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    /**
     * Generate response based on user query and system threats
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0007H\u00c6\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0010\u00a8\u0006\u001b"}, d2 = {"Lcom/eps/android/analysis/ai/OfflineAiEngine$DeviceStatus;", "", "batteryLevel", "", "freeStorageGb", "", "isCharging", "", "connectionMode", "isVpnActive", "(ILjava/lang/String;ZLjava/lang/String;Z)V", "getBatteryLevel", "()I", "getConnectionMode", "()Ljava/lang/String;", "getFreeStorageGb", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
    public static final class DeviceStatus {
        private final int batteryLevel = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String freeStorageGb = null;
        private final boolean isCharging = false;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String connectionMode = null;
        private final boolean isVpnActive = false;
        
        public DeviceStatus(int batteryLevel, @org.jetbrains.annotations.NotNull
        java.lang.String freeStorageGb, boolean isCharging, @org.jetbrains.annotations.NotNull
        java.lang.String connectionMode, boolean isVpnActive) {
            super();
        }
        
        public final int getBatteryLevel() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getFreeStorageGb() {
            return null;
        }
        
        public final boolean isCharging() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getConnectionMode() {
            return null;
        }
        
        public final boolean isVpnActive() {
            return false;
        }
        
        public final int component1() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        public final boolean component3() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.OfflineAiEngine.DeviceStatus copy(int batteryLevel, @org.jetbrains.annotations.NotNull
        java.lang.String freeStorageGb, boolean isCharging, @org.jetbrains.annotations.NotNull
        java.lang.String connectionMode, boolean isVpnActive) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemAction;", "", "type", "Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemActionType;", "data", "", "(Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemActionType;Ljava/lang/String;)V", "getData", "()Ljava/lang/String;", "getType", "()Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemActionType;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class SystemAction {
        @org.jetbrains.annotations.NotNull
        private final com.eps.android.analysis.ai.OfflineAiEngine.SystemActionType type = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String data = null;
        
        public SystemAction(@org.jetbrains.annotations.NotNull
        com.eps.android.analysis.ai.OfflineAiEngine.SystemActionType type, @org.jetbrains.annotations.Nullable
        java.lang.String data) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.OfflineAiEngine.SystemActionType getType() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getData() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.OfflineAiEngine.SystemActionType component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.OfflineAiEngine.SystemAction copy(@org.jetbrains.annotations.NotNull
        com.eps.android.analysis.ai.OfflineAiEngine.SystemActionType type, @org.jetbrains.annotations.Nullable
        java.lang.String data) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemActionType;", "", "(Ljava/lang/String;I)V", "OPEN_APP", "START_SCAN", "TOGGLE_VPN", "SHOW_SECURITY_AUDIT", "NAVIGATE", "NONE", "app_debug"})
    public static enum SystemActionType {
        /*public static final*/ OPEN_APP /* = new OPEN_APP() */,
        /*public static final*/ START_SCAN /* = new START_SCAN() */,
        /*public static final*/ TOGGLE_VPN /* = new TOGGLE_VPN() */,
        /*public static final*/ SHOW_SECURITY_AUDIT /* = new SHOW_SECURITY_AUDIT() */,
        /*public static final*/ NAVIGATE /* = new NAVIGATE() */,
        /*public static final*/ NONE /* = new NONE() */;
        
        SystemActionType() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.eps.android.analysis.ai.OfflineAiEngine.SystemActionType> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00032\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0017"}, d2 = {"Lcom/eps/android/analysis/ai/OfflineAiEngine$VishingAnalysisResult;", "", "isDangerous", "", "score", "", "message", "", "(ZDLjava/lang/String;)V", "()Z", "getMessage", "()Ljava/lang/String;", "getScore", "()D", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class VishingAnalysisResult {
        private final boolean isDangerous = false;
        private final double score = 0.0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String message = null;
        
        public VishingAnalysisResult(boolean isDangerous, double score, @org.jetbrains.annotations.NotNull
        java.lang.String message) {
            super();
        }
        
        public final boolean isDangerous() {
            return false;
        }
        
        public final double getScore() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getMessage() {
            return null;
        }
        
        public final boolean component1() {
            return false;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.OfflineAiEngine.VishingAnalysisResult copy(boolean isDangerous, double score, @org.jetbrains.annotations.NotNull
        java.lang.String message) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}