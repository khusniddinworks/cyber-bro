# 🛡️ Cyber Brother PRO

> O'zbekiston uchun birinchi AI-asoslangan mobil xavfsizlik ekotizimi

## 📱 Haqida

**Cyber Brother PRO** — bu 100% offline ishlaydigan Android xavfsizlik ilovasi. Barcha tahlillar qurilmada amalga oshiriladi, hech qanday ma'lumot serverga yuborilmaydi.

## ✨ Asosiy Xususiyatlar

| Funksiya | Tavsif |
|----------|--------|
| 🔍 **APK Tahlil** | Real DEX feature extraction va heuristic tahlil |
| 🛡️ **VPN DNS Shield** | Zararli saytlarni DNS darajasida bloklash |
| 🔐 **File Vault** | AES-256-GCM shifrlash |
| 📱 **SMS Himoyasi** | Fishing SMS xabarlarini aniqlash |
| 🤖 **AI Mentor** | Rule-based xavfsizlik maslahatchi (UZ/RU/EN) |
| 🔔 **Real-time Monitoring** | Yangi o'rnatilgan ilovalarni avtomatik tekshirish |

## 🏗️ Arxitektura

```
com.eps.android/
├── analysis/           # Tahlil modullari
│   ├── ai/
│   │   ├── MalwareClassifier.kt    # Heuristic APK classifier
│   │   ├── DexFeatureExtractor.kt  # DEX parsing
│   │   └── OfflineAiEngine.kt      # Rule-based AI
│   ├── StaticAnalyzer.kt
│   └── VishingPatternMatcher.kt
├── core/               # Tizim servislari
│   ├── AntiPhishingVpnService.kt
│   ├── EpsMonitoringService.kt
│   └── ...
├── data/               # Room Database
├── ui/                 # Jetpack Compose UI
└── utils/              # Yordamchi klasslar
```

## 🛠️ Texnologiyalar

- **Til:** Kotlin 1.9+
- **UI:** Jetpack Compose + Material3
- **DI:** Hilt (Dagger)
- **Database:** Room
- **Background:** WorkManager + ForegroundService
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 34 (Android 14)

## 📦 Build

```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease
```

## 🌐 Tillar

- 🇺🇿 O'zbekcha
- 🇷🇺 Русский  
- 🇺🇸 English

## 📄 Litsenziya

Proprietary - Barcha huquqlar himoyalangan

## 👥 Jamoa

Cyber Brother Team © 2026
