# 🦅 Cyber Brother ULTRA — Loyiha Hujjatlari (Master Documentation)
**Versiya:** 1.4.0 (ULTRA)  
**Status:** Active / Production  
**Muallif:** Cyber Brother Team (Xamidov Khusniddin)

---

## 📖 1. Loyiha Missiyasi va Maqsadi
**Cyber Brother ULTRA** — bu shunchaki antivirus emas, balki sun'iy intellekt (AI) asosida ishlaydigan **faol kiber-qalqon**.  
Bizning asosiy farqimiz — **"Zero-Trust"** (Hech kimga ishonmaslik) siyosati.

Biz foydalanuvchini hatto o'zidan ham himoya qilamiz:  
Agar foydalanuvchi bilmasdan virusli ilovani o'rnatmoqchi bo'lsa, **Cyber Brother bu jarayonni jismonan to'xtatib qoladi.**

---

## 🏗️ 2. Tizim Arxitekturasi (Yangilangan)

Loyiha 4 ta asosiy "ustun" ustiga qurilgan:

1.  **Mobile App (Android):** Asosiy himoya va bloklash tizimi.
2.  **Accessibility Core:** Tizimning "ko'zi" va "qo'li" (bosishlarni nazorat qiladi).
3.  **Telegram Bot:** Litsenziya va tarqatish markazi.
4.  **Web Admin Panel:** Kuzatuv minorasi.

```mermaid
graph TD
    User((Foydalanuvchi))
    App[📱 Android Ilova]
    Core[� Accessibility Core]
    Bot[🤖 Telegram Bot]
    
    User -->|Telegramdan APK ochadi| Core
    Core -->|Bloklaydi (HOME Action)| User
    
    User -->|Ilova o'rnatadi| App
    App -->|Deep DEX Scan (200%)| App
    App -->|Qizil Ekran (Red Alert)| User
```

---

## 🛠️ 3. Yangi "ULTRA" Funksiyalar (v1.4.0)

### A. � Anti-Sideloading Shield (Telegram Bloklash)
Bu funksiya telefonni "yopiq tizim"ga aylantiradi (iPhone kabi).
*   **Muammo:** Foydalanuvchilar Telegram, Shareit yoki Fayl menejeri orqali virusli APKlarni bilmasdan o'rnatib qo'yadi.
*   **Yechim:** Cyber Brother **Package Installer** (O'rnatuvchi) oynasi ochilganini sezadi.
*   **Reaksiya:**
    1.  Oyna ochilishi bilan **0.1 soniyada** "HOME" (Uyga) tugmasini bosadi.
    2.  O'rnatish oynasi shartta yopilib ketadi.
    3.  Foydalanuvchiga: *"⛔ O'rnatish Bloklandi! Faqat Play Marketdan ruxsat bor"* degan xabar chiqadi.
*   **Qamrov:** Google, Samsung, Xiaomi (MIUI), Huawei, Oppo o'rnatuvchilarini taniydi.

### B. � AI Deep Scan (200% DEX Analysis)
Oldingi versiyalar faqat fayl nomini ko'rardi. Yangi avlod esa fayl ichiga kiradi.
*   **Texnologiya:** Ilova o'rnatilishidan oldin uning **`classes.dex`** (boshqaruv kodi) faylini o'qiydi (2MB gacha).
*   **Nimani qidiradi?**
    *   `SmsManager` (Yashirin SMS yuborish).
    *   `Camera` (Yashirin rasmga olish).
    *   `LocationManager` (GPS kuzatuv).
    *   `DexClassLoader` (Dinamik kod yuklash - viruslarning asosiy belgisi).
*   **Natija:** Agar bu kodlar topilsa, ilova o'rnatilishi **bloklanadi**.

### C. � Red Alert Protocol (Qizil Ekran)
Bildirishnomalarni (Notification) foydalanuvchilar ko'pincha o'tkazib yuboradi. Shuning uchun biz radikal choraga o'tdik.
*   **Ishlash tamoyili:** Xavfli ilova o'rnatilishi bilan **Full-Screen Intent** ishga tushadi.
*   **Ko'rinish:** Ekran qop-qora yoki qizil bo'lib, o'rtada katta **"XAVFLI ILOVA"** yozuvi va **"O'CHIRISH"** tugmasi chiqadi.
*   **Majburiylik:** Bu oynani yopish qiyin, foydalanuvchi "O'chirish"ni bosishga majbur bo'ladi.

---

## 🔒 4. Xavfsizlik Protokollari

| Xavf Turi | Bizning Yechim |
| :--- | :--- |
| **Sideloading (APK o'rnatish)** | **Package Installer Killing** (Jarayonni o'ldirish) |
| **Spyware (Josuslik)** | **Heuristic DEX Analysis** (Kod tahlili) |
| **Phishing (Soxta sayt)** | **Accessibility URL Monitor** (Saytlarni tekshirish) |
| **Vishing (Tel. firibgarlik)** | **Call Text Analysis** (Gaplarni tahlil qilish) |

---

## 💰 5. Monetizatsiya (Freemium)

1.  **CORE (Bepul):**
    *   Virus skaneri.
    *   App Audit.
2.  **ULTRA (Premium - 50,000 so'm/oy):**
    *   **Anti-Sideloading** (Telegram bloklash).
    *   **Deep DEX Scan.**
    *   **Red Alert.**
    *   24/7 AI Monitoring.

## 🛡️ 6. Ilovani 100% Himoya qilish yo'riqnomasi (10 Qadam)

Foydalanuvchi tizimni to'liq himoya qilish uchun quyidagi bosqichlarni amalga oshirishi shart:

1.  **Fayllarga ruxsat:** Ilova ichida "Barcha fayllarni boshqarish" ruxsatini yoqing.
2.  **Dashboard sozlamalari:** Asosiy ekranda "Xavfli fayllar filtri" va boshqa ruxsatlarni tasdiqlang.
3.  **Bildirishnomalar:** "Qurilma va ilova bildirishnomalari" bo'limidan Cyber Brotherga ruxsat bering.
4.  **Tizim Sozlamalari:** Telefoningizning umumiy Sozlamalar (Settings) menyusiga kiring.
5.  **Ilovalar:** "Ilova boshqaruvi" (App Management) bo'limini tanlang.
6.  **Ilova tanlash:** Ro'yxatdan "Cyber Brother" ilovasini toping va kiring.
7.  **3-nuqta menusi:** Yuqori o'ng burchakdagi 3 ta nuqta (ko'proq) tugmasini bosing.
8.  **Cheklovni yechish:** "Cheklangan sozlamalarga ruxsat berish" (Allow restricted settings) bandini tanlang.
9.  **Ilovaga qaytish:** Cyber Brotherga qayting va "Fishingdan himoya" tugmasini bosing.
10. **Maxsus imkoniyatlar:** "Accessibility" bo'limidan "Cyber Brother Phishing Protection" xizmatini yoqing.

---

*Hujjat yangilandi: 2026-yil 25-fevral*  
**Cyber Brother Security Team**
