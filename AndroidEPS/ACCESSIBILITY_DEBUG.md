# AccessibilityService Debug Qo'llanma

## Muammo
Telegram ga kirib ketayapti - AccessibilityService URL larni ushlamayapti.

## Ehtimoliy Sabablar va Yechimlar

### 1. Accessibility Ruxsati Berilmaganmi?

**Tekshirish:**
```bash
adb shell settings get secure enabled_accessibility_services
```

**Natija qanday bo'lishi kerak:**
```
com.eps.android/com.eps.android.core.PhishingAccessibilityService
```

**Agar bo'sh bo'lsa:**
- Settings → Accessibility → HACKDEFENDER → ON qiling

---

### 2. Package Name Noto'g'ri

Telegram ning to'g'ri package name: `org.telegram.messenger`

**Tekshirish:**
```bash
adb shell dumpsys window | grep -i "mCurrentFocus"
```

Telegram ochiq bo'lganda natija:
```
mCurrentFocus=Window{... org.telegram.messenger/...}
```

**Muammo:** `accessibility_service_config.xml` da faqat ma'lum ilovalar ro'yxati bor.

**Yechim:** Barcha ilovalarda ishlashi uchun `packageNames` ni o'chirish kerak!

---

### 3. Event Type Muammosi

Hozirgi konfiguratsiya:
```xml
android:accessibilityEventTypes="typeWindowContentChanged|typeWindowStateChanged"
```

Bu yetarli emas. Telegram da matn paydo bo'lganda `TYPE_VIEW_TEXT_CHANGED` ham kerak.

---

### 4. Logcat Tekshirish

**Xizmat ishga tushganmi?**
```bash
adb logcat -s PhishingAccessibilityService:* Timber:*
```

**Kutilayotgan log:**
```
I/PhishingAccessibilityService: PhishingAccessibilityService connected
I/PhishingAccessibilityService: Loaded 8 blacklisted domains
```

**Agar log bo'lmasa:** Xizmat ishga tushmagan.

---

## Tuzatish Rejasi

### 1. XML Konfiguratsiyani Yangilash
`accessibility_service_config.xml` ni to'liq ochiq qilish:
- ✅ Barcha event typelarni qo'shish
- ✅ `packageNames` ni olib tashlash (barcha ilovalar)
- ✅ Feedback type ni to'g'rilash

### 2. Service Kodini Yangilash
- ✅ Ko'proq event typelarni qo'llab-quvvatlash
- ✅ Debug loglarni qo'shish
- ✅ URL extraction logikasini yaxshilash

### 3. Test Strategiyasi
1. Logcat ochib qo'yish
2. Telegram ochish
3. Test link yuborish: `payme-bonus.tk`
4. Logda "Found URLs" ko'rinishi kerak

---

## Tezkor Yechim

Eng katta muammo - `packageNames` cheklovi. Uni olib tashlash kerak!
