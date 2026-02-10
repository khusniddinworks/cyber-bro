package com.eps.android.analysis.ai;

/**
 * PRO DEVICE ANALYZER - Qurilmani 100% tahlil qiluvchi AI Core
 *
 * Bu modul qurilmadagi barcha jarayonlarni tahlil qiladi va 
 * foydalanuvchiga tushunarli tilda ma'lumot beradi.
 */
@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0003/01B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006J\u0018\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J\b\u0010\u0010\u001a\u00020\u0006H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0014H\u0002J\b\u0010\u0016\u001a\u00020\u0014H\u0002J\b\u0010\u0017\u001a\u00020\fH\u0002J\b\u0010\u0018\u001a\u00020\u0006H\u0002J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000eH\u0002J\b\u0010\u001b\u001a\u00020\u0012H\u0002J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000eH\u0002J\b\u0010\u001d\u001a\u00020\fH\u0002J\b\u0010\u001e\u001a\u00020\u0014H\u0002J\b\u0010\u001f\u001a\u00020\u0006H\u0002J\b\u0010 \u001a\u00020\u0012H\u0002J\b\u0010!\u001a\u00020\u0012H\u0002J\b\u0010\"\u001a\u00020\fH\u0002J\b\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020$H\u0002J\b\u0010&\u001a\u00020$H\u0002J\b\u0010\'\u001a\u00020$H\u0002J\b\u0010(\u001a\u00020$H\u0002J\b\u0010)\u001a\u00020$H\u0002J\b\u0010*\u001a\u00020$H\u0002J\b\u0010+\u001a\u00020$H\u0002J\b\u0010,\u001a\u00020$H\u0002J\u0006\u0010-\u001a\u00020.R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/eps/android/analysis/ai/DeviceAnalyzer;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "generateHumanReadableReport", "", "lang", "generateProcessExplanation", "processName", "importance", "getAvailableRam", "", "getBackgroundProcesses", "", "Lcom/eps/android/analysis/ai/DeviceAnalyzer$ProcessInfo;", "getBatteryHealth", "getBatteryLevel", "", "getBatteryTemperature", "", "getCpuUsage", "getFreeStorage", "getMobileDataUsage", "getNetworkType", "getRecentlyInstalledApps", "Lcom/eps/android/analysis/ai/DeviceAnalyzer$AppInfo;", "getRunningAppsCount", "getSuspiciousApps", "getTotalRam", "getTotalStorage", "getUptime", "getUsedRamPercent", "getUsedStoragePercent", "getWifiDataUsage", "isAdbEnabled", "", "isCharging", "isDevModeEnabled", "isDeviceRooted", "isHotspotActive", "isScreenLockSet", "isUnknownSourcesAllowed", "isUsbTethering", "isVpnActive", "performFullAnalysis", "Lcom/eps/android/analysis/ai/DeviceAnalyzer$FullDeviceReport;", "AppInfo", "FullDeviceReport", "ProcessInfo", "app_debug"})
public final class DeviceAnalyzer {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    
    @javax.inject.Inject
    public DeviceAnalyzer(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Qurilmani to'liq skanerlash
     */
    @org.jetbrains.annotations.NotNull
    public final com.eps.android.analysis.ai.DeviceAnalyzer.FullDeviceReport performFullAnalysis() {
        return null;
    }
    
    private final int getBatteryLevel() {
        return 0;
    }
    
    private final boolean isCharging() {
        return false;
    }
    
    private final float getBatteryTemperature() {
        return 0.0F;
    }
    
    private final java.lang.String getBatteryHealth() {
        return null;
    }
    
    private final float getTotalStorage() {
        return 0.0F;
    }
    
    private final float getFreeStorage() {
        return 0.0F;
    }
    
    private final int getUsedStoragePercent() {
        return 0;
    }
    
    private final long getTotalRam() {
        return 0L;
    }
    
    private final long getAvailableRam() {
        return 0L;
    }
    
    private final int getUsedRamPercent() {
        return 0;
    }
    
    private final float getCpuUsage() {
        return 0.0F;
    }
    
    private final java.lang.String getNetworkType() {
        return null;
    }
    
    private final boolean isVpnActive() {
        return false;
    }
    
    private final boolean isUsbTethering() {
        return false;
    }
    
    private final boolean isHotspotActive() {
        return false;
    }
    
    private final long getMobileDataUsage() {
        return 0L;
    }
    
    private final long getWifiDataUsage() {
        return 0L;
    }
    
    private final int getRunningAppsCount() {
        return 0;
    }
    
    private final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.ProcessInfo> getBackgroundProcesses() {
        return null;
    }
    
    private final java.lang.String generateProcessExplanation(java.lang.String processName, java.lang.String importance) {
        return null;
    }
    
    private final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> getRecentlyInstalledApps() {
        return null;
    }
    
    private final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> getSuspiciousApps() {
        return null;
    }
    
    private final boolean isDevModeEnabled() {
        return false;
    }
    
    private final boolean isAdbEnabled() {
        return false;
    }
    
    private final boolean isUnknownSourcesAllowed() {
        return false;
    }
    
    private final boolean isScreenLockSet() {
        return false;
    }
    
    private final boolean isDeviceRooted() {
        return false;
    }
    
    private final java.lang.String getUptime() {
        return null;
    }
    
    /**
     * Foydalanuvchiga tushunarli tilda to'liq hisobot
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String generateHumanReadableReport(@org.jetbrains.annotations.NotNull
    java.lang.String lang) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b\u00a8\u0006\u0018"}, d2 = {"Lcom/eps/android/analysis/ai/DeviceAnalyzer$AppInfo;", "", "name", "", "packageName", "isSystemApp", "", "riskLevel", "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;)V", "()Z", "getName", "()Ljava/lang/String;", "getPackageName", "getRiskLevel", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class AppInfo {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String packageName = null;
        private final boolean isSystemApp = false;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String riskLevel = null;
        
        public AppInfo(@org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String packageName, boolean isSystemApp, @org.jetbrains.annotations.NotNull
        java.lang.String riskLevel) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPackageName() {
            return null;
        }
        
        public final boolean isSystemApp() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getRiskLevel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
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
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo copy(@org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String packageName, boolean isSystemApp, @org.jetbrains.annotations.NotNull
        java.lang.String riskLevel) {
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
     * To'liq qurilma holati
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\bG\b\u0086\b\u0018\u00002\u00020\u0001B\u00ff\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0007\u0012\u0006\u0010\u0012\u001a\u00020\t\u0012\u0006\u0010\u0013\u001a\u00020\u0005\u0012\u0006\u0010\u0014\u001a\u00020\u0005\u0012\u0006\u0010\u0015\u001a\u00020\u0005\u0012\u0006\u0010\u0016\u001a\u00020\u000e\u0012\u0006\u0010\u0017\u001a\u00020\u000e\u0012\u0006\u0010\u0018\u001a\u00020\u0003\u0012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a\u0012\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001a\u0012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001a\u0012\u0006\u0010\u001f\u001a\u00020\u0005\u0012\u0006\u0010 \u001a\u00020\u0005\u0012\u0006\u0010!\u001a\u00020\u0005\u0012\u0006\u0010\"\u001a\u00020\u0005\u0012\u0006\u0010#\u001a\u00020\u0005\u0012\u0006\u0010$\u001a\u00020\t\u0012\u0006\u0010%\u001a\u00020\t\u0012\u0006\u0010&\u001a\u00020\t\u00a2\u0006\u0002\u0010\'J\t\u0010B\u001a\u00020\u0003H\u00c6\u0003J\t\u0010C\u001a\u00020\u0003H\u00c6\u0003J\t\u0010D\u001a\u00020\u0007H\u00c6\u0003J\t\u0010E\u001a\u00020\tH\u00c6\u0003J\t\u0010F\u001a\u00020\u0005H\u00c6\u0003J\t\u0010G\u001a\u00020\u0005H\u00c6\u0003J\t\u0010H\u001a\u00020\u0005H\u00c6\u0003J\t\u0010I\u001a\u00020\u000eH\u00c6\u0003J\t\u0010J\u001a\u00020\u000eH\u00c6\u0003J\t\u0010K\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u00c6\u0003J\t\u0010M\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010N\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001aH\u00c6\u0003J\u000f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001aH\u00c6\u0003J\t\u0010P\u001a\u00020\u0005H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0005H\u00c6\u0003J\t\u0010R\u001a\u00020\u0005H\u00c6\u0003J\t\u0010S\u001a\u00020\u0005H\u00c6\u0003J\t\u0010T\u001a\u00020\u0005H\u00c6\u0003J\t\u0010U\u001a\u00020\tH\u00c6\u0003J\t\u0010V\u001a\u00020\tH\u00c6\u0003J\t\u0010W\u001a\u00020\tH\u00c6\u0003J\t\u0010X\u001a\u00020\u0007H\u00c6\u0003J\t\u0010Y\u001a\u00020\tH\u00c6\u0003J\t\u0010Z\u001a\u00020\u0007H\u00c6\u0003J\t\u0010[\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\\\u001a\u00020\u0003H\u00c6\u0003J\t\u0010]\u001a\u00020\u000eH\u00c6\u0003J\t\u0010^\u001a\u00020\u000eH\u00c6\u0003J\u00bd\u0002\u0010_\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u000e2\b\b\u0002\u0010\u0017\u001a\u00020\u000e2\b\b\u0002\u0010\u0018\u001a\u00020\u00032\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u000e\b\u0002\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001a2\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001a2\b\b\u0002\u0010\u001f\u001a\u00020\u00052\b\b\u0002\u0010 \u001a\u00020\u00052\b\b\u0002\u0010!\u001a\u00020\u00052\b\b\u0002\u0010\"\u001a\u00020\u00052\b\b\u0002\u0010#\u001a\u00020\u00052\b\b\u0002\u0010$\u001a\u00020\t2\b\b\u0002\u0010%\u001a\u00020\t2\b\b\u0002\u0010&\u001a\u00020\tH\u00c6\u0001J\u0013\u0010`\u001a\u00020\u00052\b\u0010a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010b\u001a\u00020\u0003H\u00d6\u0001J\t\u0010c\u001a\u00020\tH\u00d6\u0001R\u0011\u0010$\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010)R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0011\u0010\u0011\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00102R\u0011\u0010%\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010)R\u0011\u0010\u000b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00102R\u0011\u0010 \u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u00106R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u00106R\u0011\u0010\u001f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u00106R\u0011\u0010\u0015\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u00106R\u0011\u0010#\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u00106R\u0011\u0010\"\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u00106R\u0011\u0010!\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u00106R\u0011\u0010\u0014\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u00106R\u0011\u0010\u0013\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u00106R\u0011\u0010\u0016\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010+R\u0011\u0010\u0012\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010)R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010-R\u0011\u0010\u0018\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u00100R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010-R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010+R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u00102R\u0011\u0010&\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010)R\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u00100R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u00100R\u0011\u0010\u0017\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010+\u00a8\u0006d"}, d2 = {"Lcom/eps/android/analysis/ai/DeviceAnalyzer$FullDeviceReport;", "", "batteryLevel", "", "isCharging", "", "batteryTemperature", "", "batteryHealth", "", "totalStorageGb", "freeStorageGb", "usedStoragePercent", "totalRamMb", "", "availableRamMb", "usedRamPercent", "cpuUsagePercent", "networkType", "isVpnActive", "isUsbTethering", "isHotspotActive", "mobileDataUsedMb", "wifiDataUsedMb", "runningAppsCount", "backgroundProcesses", "", "Lcom/eps/android/analysis/ai/DeviceAnalyzer$ProcessInfo;", "recentlyInstalledApps", "Lcom/eps/android/analysis/ai/DeviceAnalyzer$AppInfo;", "suspiciousApps", "isDevModeEnabled", "isAdbEnabled", "isUnknownSourcesAllowed", "isScreenLockSet", "isRooted", "androidVersion", "deviceModel", "uptime", "(IZFLjava/lang/String;FFIJJIFLjava/lang/String;ZZZJJILjava/util/List;Ljava/util/List;Ljava/util/List;ZZZZZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAndroidVersion", "()Ljava/lang/String;", "getAvailableRamMb", "()J", "getBackgroundProcesses", "()Ljava/util/List;", "getBatteryHealth", "getBatteryLevel", "()I", "getBatteryTemperature", "()F", "getCpuUsagePercent", "getDeviceModel", "getFreeStorageGb", "()Z", "getMobileDataUsedMb", "getNetworkType", "getRecentlyInstalledApps", "getRunningAppsCount", "getSuspiciousApps", "getTotalRamMb", "getTotalStorageGb", "getUptime", "getUsedRamPercent", "getUsedStoragePercent", "getWifiDataUsedMb", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
    public static final class FullDeviceReport {
        private final int batteryLevel = 0;
        private final boolean isCharging = false;
        private final float batteryTemperature = 0.0F;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String batteryHealth = null;
        private final float totalStorageGb = 0.0F;
        private final float freeStorageGb = 0.0F;
        private final int usedStoragePercent = 0;
        private final long totalRamMb = 0L;
        private final long availableRamMb = 0L;
        private final int usedRamPercent = 0;
        private final float cpuUsagePercent = 0.0F;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String networkType = null;
        private final boolean isVpnActive = false;
        private final boolean isUsbTethering = false;
        private final boolean isHotspotActive = false;
        private final long mobileDataUsedMb = 0L;
        private final long wifiDataUsedMb = 0L;
        private final int runningAppsCount = 0;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.ProcessInfo> backgroundProcesses = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> recentlyInstalledApps = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> suspiciousApps = null;
        private final boolean isDevModeEnabled = false;
        private final boolean isAdbEnabled = false;
        private final boolean isUnknownSourcesAllowed = false;
        private final boolean isScreenLockSet = false;
        private final boolean isRooted = false;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String androidVersion = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String deviceModel = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String uptime = null;
        
        public FullDeviceReport(int batteryLevel, boolean isCharging, float batteryTemperature, @org.jetbrains.annotations.NotNull
        java.lang.String batteryHealth, float totalStorageGb, float freeStorageGb, int usedStoragePercent, long totalRamMb, long availableRamMb, int usedRamPercent, float cpuUsagePercent, @org.jetbrains.annotations.NotNull
        java.lang.String networkType, boolean isVpnActive, boolean isUsbTethering, boolean isHotspotActive, long mobileDataUsedMb, long wifiDataUsedMb, int runningAppsCount, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.ProcessInfo> backgroundProcesses, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> recentlyInstalledApps, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> suspiciousApps, boolean isDevModeEnabled, boolean isAdbEnabled, boolean isUnknownSourcesAllowed, boolean isScreenLockSet, boolean isRooted, @org.jetbrains.annotations.NotNull
        java.lang.String androidVersion, @org.jetbrains.annotations.NotNull
        java.lang.String deviceModel, @org.jetbrains.annotations.NotNull
        java.lang.String uptime) {
            super();
        }
        
        public final int getBatteryLevel() {
            return 0;
        }
        
        public final boolean isCharging() {
            return false;
        }
        
        public final float getBatteryTemperature() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getBatteryHealth() {
            return null;
        }
        
        public final float getTotalStorageGb() {
            return 0.0F;
        }
        
        public final float getFreeStorageGb() {
            return 0.0F;
        }
        
        public final int getUsedStoragePercent() {
            return 0;
        }
        
        public final long getTotalRamMb() {
            return 0L;
        }
        
        public final long getAvailableRamMb() {
            return 0L;
        }
        
        public final int getUsedRamPercent() {
            return 0;
        }
        
        public final float getCpuUsagePercent() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getNetworkType() {
            return null;
        }
        
        public final boolean isVpnActive() {
            return false;
        }
        
        public final boolean isUsbTethering() {
            return false;
        }
        
        public final boolean isHotspotActive() {
            return false;
        }
        
        public final long getMobileDataUsedMb() {
            return 0L;
        }
        
        public final long getWifiDataUsedMb() {
            return 0L;
        }
        
        public final int getRunningAppsCount() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.ProcessInfo> getBackgroundProcesses() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> getRecentlyInstalledApps() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> getSuspiciousApps() {
            return null;
        }
        
        public final boolean isDevModeEnabled() {
            return false;
        }
        
        public final boolean isAdbEnabled() {
            return false;
        }
        
        public final boolean isUnknownSourcesAllowed() {
            return false;
        }
        
        public final boolean isScreenLockSet() {
            return false;
        }
        
        public final boolean isRooted() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getAndroidVersion() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDeviceModel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getUptime() {
            return null;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final int component10() {
            return 0;
        }
        
        public final float component11() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component12() {
            return null;
        }
        
        public final boolean component13() {
            return false;
        }
        
        public final boolean component14() {
            return false;
        }
        
        public final boolean component15() {
            return false;
        }
        
        public final long component16() {
            return 0L;
        }
        
        public final long component17() {
            return 0L;
        }
        
        public final int component18() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.ProcessInfo> component19() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> component20() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> component21() {
            return null;
        }
        
        public final boolean component22() {
            return false;
        }
        
        public final boolean component23() {
            return false;
        }
        
        public final boolean component24() {
            return false;
        }
        
        public final boolean component25() {
            return false;
        }
        
        public final boolean component26() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component27() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component28() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component29() {
            return null;
        }
        
        public final float component3() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component4() {
            return null;
        }
        
        public final float component5() {
            return 0.0F;
        }
        
        public final float component6() {
            return 0.0F;
        }
        
        public final int component7() {
            return 0;
        }
        
        public final long component8() {
            return 0L;
        }
        
        public final long component9() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.DeviceAnalyzer.FullDeviceReport copy(int batteryLevel, boolean isCharging, float batteryTemperature, @org.jetbrains.annotations.NotNull
        java.lang.String batteryHealth, float totalStorageGb, float freeStorageGb, int usedStoragePercent, long totalRamMb, long availableRamMb, int usedRamPercent, float cpuUsagePercent, @org.jetbrains.annotations.NotNull
        java.lang.String networkType, boolean isVpnActive, boolean isUsbTethering, boolean isHotspotActive, long mobileDataUsedMb, long wifiDataUsedMb, int runningAppsCount, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.ProcessInfo> backgroundProcesses, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> recentlyInstalledApps, @org.jetbrains.annotations.NotNull
        java.util.List<com.eps.android.analysis.ai.DeviceAnalyzer.AppInfo> suspiciousApps, boolean isDevModeEnabled, boolean isAdbEnabled, boolean isUnknownSourcesAllowed, boolean isScreenLockSet, boolean isRooted, @org.jetbrains.annotations.NotNull
        java.lang.String androidVersion, @org.jetbrains.annotations.NotNull
        java.lang.String deviceModel, @org.jetbrains.annotations.NotNull
        java.lang.String uptime) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u0019"}, d2 = {"Lcom/eps/android/analysis/ai/DeviceAnalyzer$ProcessInfo;", "", "name", "", "memoryMb", "", "importance", "explanation", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getExplanation", "()Ljava/lang/String;", "getImportance", "getMemoryMb", "()I", "getName", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class ProcessInfo {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        private final int memoryMb = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String importance = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String explanation = null;
        
        public ProcessInfo(@org.jetbrains.annotations.NotNull
        java.lang.String name, int memoryMb, @org.jetbrains.annotations.NotNull
        java.lang.String importance, @org.jetbrains.annotations.NotNull
        java.lang.String explanation) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        public final int getMemoryMb() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getImportance() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getExplanation() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.analysis.ai.DeviceAnalyzer.ProcessInfo copy(@org.jetbrains.annotations.NotNull
        java.lang.String name, int memoryMb, @org.jetbrains.annotations.NotNull
        java.lang.String importance, @org.jetbrains.annotations.NotNull
        java.lang.String explanation) {
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