/* ===== CYBER BROTHER PRO - PREMIUM JS ENGINE ===== */

const translations = {
    uz: {
        nav_home: "Asosiy",
        nav_about: "Haqida",
        nav_features: "Funksiyalar",
        nav_contact: "Aloqa",
        nav_download: "Yuklab olish",
        hero_badge: "✨ YANGI DAVR XAVFSIZLIGI",
        hero_title: "Oqilona himoya va <span class='highlight'>mutlaq maxfiylik</span>",
        hero_subtitle: "Raqamli hayotingizni yagona ilovada 'Zero-Knowledge' tamoyillari asosida, 100% offline quvvatda boshqaring.",
        hero_btn: "Ilovani yuklab olish",
        about_title: "Bizning Missiyamiz",
        about_desc: "Cyber Brother PRO - bu shunchaki antivirus emas. Bu sizning raqamli dunyodagi shaxsiy qo'riqchingiz. Bizning asosiy maqsadimiz - foydalanuvchi ma'lumotlarini 100% maxfiy saqlagan holda, eng zamonaviy tahdidlardan himoya qilishdir.",
        stat_1_val: "100%",
        stat_1_lbl: "Offline Skan",
        stat_2_val: "0",
        stat_2_lbl: "Data Leak",
        stat_3_val: "24/7",
        stat_3_lbl: "Faol Himoya",
        feat_main_title: "Asosiy Imkoniyatlar",
        feat_1_title: "Offline AI Engine",
        feat_1_desc: "Hech qanday ma'lumot bulutga chiqmaydi. Tahlillarni telefoningizning o'zi bajaradi.",
        feat_2_title: "Anti-Vishing Guard",
        feat_2_desc: "SMS kodlarni va bank ma'lumotlarini jinoyatchilardan himoya qiluvchi o'ta kuchli tizim.",
        feat_3_title: "Smart App Audit",
        feat_3_desc: "O'rnatilgan ilovalarning xavfsizlik darajasini real vaqtda tahlil qiladi.",
        feat_4_title: "Fayl Himoyasi",
        feat_4_desc: "Barcha yuklab olingan fayllarni zararli kodlar borligiga tekshiradi.",
        feat_5_title: "Maxfiy Ombor",
        feat_5_desc: "Shaxsiy fayl va rasmlaringizni maxfiy shifrlangan omborda saqlaydi.",
        feat_6_title: "Dark Mode UI",
        feat_6_desc: "Ko'zni charchatmaydigan va batareyani tejaydigan qorong'u interfeys.",
        dl_title: "Versiyalar Markazi",
        dl_latest: "SO'NGI VERSIA",
        dl_v13_desc: "Anti-Vishing va barcha yangilangan xavfsizlik funksiyalari.",
        dl_v13_f1: "✅ Yangilangan AI model",
        dl_v13_f2: "✅ Anti-Vishing v2.0",
        dl_v13_f3: "✅ Offline tahlil tezligi +30%",
        dl_prev: "OLDINGI VERSIA",
        dl_v12_desc: "Barqaror va sinalgan xavfsizlik kodlari.",
        dl_v12_f1: "✅ Asosiy malware skaner",
        dl_v12_f2: "✅ Minimalist dizayn",
        btn_dl: "YUKLAB OLISH (APK)",
        contact_title: "Savollaringiz bormi?",
        contact_subtitle: "Biz bilan bog'laning va mutaxassislarimiz sizga yordam berishadi.",
        footer_slogan: "Sizning raqamli hayotingiz, bizning mas'uliyatimiz.",
        footer_rights: "© 2026 Cyber Brother PRO. Barcha huquqlar himoyalangan.",
        // How It Works
        how_title: "Qanday Ishlaydi?",
        step_1_title: "Ilovani O'rnating",
        step_1_desc: "APK faylni yuklab oling va o'rnating. Hech qanday ro'yxatdan o'tish talab etilmaydi.",
        step_2_title: "Ruxsatlarni Bering",
        step_2_desc: "Ilova telefoningizni to'liq himoya qilishi uchun zarur ruxsatlarni so'raydi.",
        step_3_title: "Xotirjam Bo'ling",
        step_3_desc: "Cyber Brother 24/7 fon rejimida ishlaydi va har qanday tahdiddan darhol xabar beradi.",
        // Threats
        threats_title: "Nimalardan Himoya Qilamiz?",
        threat_1_title: "Vishing Hujumlari",
        threat_1_desc: "Firibgarlar qo'ng'iroq qilib, sizdan bank kodlarini so'rashadi. Biz darhol ogohlantirish beramiz.",
        threat_2_title: "Phishing Saytlar",
        threat_2_desc: "Soxta bank va ijtimoiy tarmoq saytlari. Ilova bu saytlarga kirishni bloklaydi.",
        threat_3_title: "Malware & Viruslar",
        threat_3_desc: "Zararli ilovalar va fayllar. AI modelimiz ularni o'rnatishdan oldin aniqlaydi.",
        threat_4_title: "Spy Apps",
        threat_4_desc: "Josuslik ilovalari sizning xabarlaringiz va joylashuvingizni kuzatadi. Biz ularni topamiz.",
        // Testimonials
        testimonials_title: "Foydalanuvchilar Fikri",
        test_1_text: "\"Bir marta menga bank xodimi bo'lib qo'ng'iroq qilishdi va SMS kod so'rashdi. Cyber Brother darhol qizil ekran ko'rsatdi va men aldanmadim. Rahmat!\"",
        test_1_name: "Jasur T.",
        test_1_role: "Toshkent, Tadbirkor",
        test_2_text: "\"Play Market'dan o'yinni yuklamoqchi edim, lekin bu ilovaning ichida virus borligini Cyber Brother aytdi. Haqiqatan ham xavfli ekan!\"",
        test_2_name: "Dilnoza A.",
        test_2_role: "Samarqand, Talaba",
        test_3_text: "\"Onam telefonini himoya qilish uchun o'rnatdim. Endi unga qo'ng'iroq qiluvchi firibgarlardan xavotir olmayapman.\"",
        test_3_name: "Bekzod R.",
        test_3_role: "Buxoro, Dasturchi"
    },
    ru: {
        nav_home: "Главная",
        nav_about: "О нас",
        nav_features: "Функции",
        nav_contact: "Контакт",
        nav_download: "Скачать",
        hero_badge: "✨ БЕЗОПАСНОСТЬ НОВОЙ ЭРЫ",
        hero_title: "Умная защита и <span class='highlight'>полная анонимность</span>",
        hero_subtitle: "Управляйте своей цифровой жизнью на основе принципов 'Zero-Knowledge' в одном приложении, на 100% в офлайн-режиме.",
        hero_btn: "Скачать приложение",
        about_title: "Наша Миссия",
        about_desc: "Cyber Brother PRO - это не просто антивирус. Это ваш личный охранник в цифровом мире. Наша главная цель - защита от самых современных угроз при полной конфиденциальности данных.",
        stat_1_val: "100%",
        stat_1_lbl: "Оффлайн Скан",
        stat_2_val: "0",
        stat_2_lbl: "Утечек",
        stat_3_val: "24/7",
        stat_3_lbl: "Активная Защита",
        feat_main_title: "Основные возможности",
        feat_1_title: "Offline AI Engine",
        feat_1_desc: "Никакие данные не уходят в облако. Анализ выполняется на вашем устройстве.",
        feat_2_title: "Anti-Vishing Guard",
        feat_2_desc: "Система защиты SMS-кодов и банковских данных от мошенников в режиме реального времени.",
        feat_3_title: "Smart App Audit",
        feat_3_desc: "Анализирует уровень безопасности установленных приложений в реальном времени.",
        feat_4_title: "Защита файлов",
        feat_4_desc: "Проверяет все загружаемые файлы на наличие вредоносного кода.",
        feat_5_title: "Секретное хранилище",
        feat_5_desc: "Храните личные файлы и фотографии в зашифрованном хранилище.",
        feat_6_title: "Dark Mode UI",
        feat_6_desc: "Тёмный интерфейс, который бережёт глаза и экономит заряд.",
        dl_title: "Центр версий",
        dl_latest: "ПОСЛЕДНЯЯ ВЕРСИЯ",
        dl_v13_desc: "Интеграция Anti-Vishing и обновленные функции безопасности.",
        dl_v13_f1: "✅ Обновленная AI модель",
        dl_v13_f2: "✅ Anti-Vishing v2.0",
        dl_v13_f3: "✅ Скорость анализа +30%",
        dl_prev: "ПРЕДЫДУЩАЯ ВЕРСИЯ",
        dl_v12_desc: "Стабильные и проверенные коды безопасности.",
        dl_v12_f1: "✅ Базовый сканер",
        dl_v12_f2: "✅ Минималистичный дизайн",
        btn_dl: "СКАЧАТЬ (APK)",
        contact_title: "Есть вопросы?",
        contact_subtitle: "Свяжитесь с нами, и наши специалисты помогут вам.",
        footer_slogan: "Ваша цифровая жизнь — наша ответственность.",
        footer_rights: "© 2026 Cyber Brother PRO. Все права защищены.",
        // How It Works
        how_title: "Как это работает?",
        step_1_title: "Установите приложение",
        step_1_desc: "Скачайте и установите APK-файл. Регистрация не требуется.",
        step_2_title: "Дайте разрешения",
        step_2_desc: "Приложение запросит необходимые разрешения для полной защиты.",
        step_3_title: "Будьте спокойны",
        step_3_desc: "Cyber Brother работает 24/7 в фоновом режиме и мгновенно предупреждает об угрозах.",
        // Threats
        threats_title: "От чего мы защищаем?",
        threat_1_title: "Вишинг-атаки",
        threat_1_desc: "Мошенники звонят и просят банковские коды. Мы мгновенно предупреждаем.",
        threat_2_title: "Фишинг-сайты",
        threat_2_desc: "Поддельные сайты банков и соцсетей. Приложение блокирует доступ к ним.",
        threat_3_title: "Malware и вирусы",
        threat_3_desc: "Вредоносные приложения и файлы. Наша AI-модель обнаруживает их заранее.",
        threat_4_title: "Шпионские приложения",
        threat_4_desc: "Следят за вашими сообщениями и местоположением. Мы их находим.",
        // Testimonials
        testimonials_title: "Отзывы пользователей",
        test_1_text: "\"Мне позвонили якобы из банка и попросили SMS-код. Cyber Brother сразу показал красный экран, и я не обманулся. Спасибо!\"",
        test_1_name: "Жасур Т.",
        test_1_role: "Ташкент, Предприниматель",
        test_2_text: "\"Хотел скачать игру из Play Market, но Cyber Brother сказал, что в ней вирус. Оказалось правда!\"",
        test_2_name: "Дилноза А.",
        test_2_role: "Самарканд, Студентка",
        test_3_text: "\"Установил на телефон мамы. Теперь не переживаю о мошенниках, которые ей звонят.\"",
        test_3_name: "Бекзод Р.",
        test_3_role: "Бухара, Программист"
    },
    en: {
        nav_home: "Home",
        nav_about: "About",
        nav_features: "Features",
        nav_contact: "Contact",
        nav_download: "Download",
        hero_badge: "✨ NEW ERA SECURITY",
        hero_title: "Smart Protection and <span class='highlight'>Absolute Privacy</span>",
        hero_subtitle: "Manage your digital life based on 'Zero-Knowledge' principles in one app, powered by 100% offline AI.",
        hero_btn: "Download App",
        about_title: "Our Mission",
        about_desc: "Cyber Brother PRO is not just an antivirus. It is your personal guardian in the digital world. Our main goal is to protect against modern threats while keeping your data 100% private.",
        stat_1_val: "100%",
        stat_1_lbl: "Offline Scan",
        stat_2_val: "0",
        stat_2_lbl: "Data Leaks",
        stat_3_val: "24/7",
        stat_3_lbl: "Active Guard",
        feat_main_title: "Key Features",
        feat_1_title: "Offline AI Engine",
        feat_1_desc: "No data goes to the cloud. Analysis is performed right on your device.",
        feat_2_title: "Anti-Vishing Guard",
        feat_2_desc: "Ultra-strict protection system for your SMS codes and bank data against fraudsters.",
        feat_3_title: "Smart App Audit",
        feat_3_desc: "Analyzes the security level of installed apps in real-time.",
        feat_4_title: "File Protection",
        feat_4_desc: "Scans all downloaded files for malicious code.",
        feat_5_title: "Privacy Vault",
        feat_5_desc: "Store your personal files and photos in an encrypted vault.",
        feat_6_title: "Dark Mode UI",
        feat_6_desc: "A sleek dark interface that's easy on the eyes and saves battery.",
        dl_title: "Version Center",
        dl_latest: "LATEST VERSION",
        dl_v13_desc: "Anti-Vishing integration and updated security features.",
        dl_v13_f1: "✅ Updated AI model",
        dl_v13_f2: "✅ Anti-Vishing v2.0",
        dl_v13_f3: "✅ Analysis speed +30%",
        dl_prev: "PREVIOUS VERSION",
        dl_v12_desc: "Stable and field-tested security codebase.",
        dl_v12_f1: "✅ Basic malware scanner",
        dl_v12_f2: "✅ Minimalist design",
        btn_dl: "DOWNLOAD (APK)",
        contact_title: "Have Questions?",
        contact_subtitle: "Contact us and our experts will assist you.",
        footer_slogan: "Your digital life, our responsibility.",
        footer_rights: "© 2026 Cyber Brother PRO. All rights reserved.",
        // How It Works
        how_title: "How It Works?",
        step_1_title: "Install the App",
        step_1_desc: "Download and install the APK file. No registration required.",
        step_2_title: "Grant Permissions",
        step_2_desc: "The app will request necessary permissions for full protection.",
        step_3_title: "Stay Protected",
        step_3_desc: "Cyber Brother runs 24/7 in the background and instantly alerts you to any threat.",
        // Threats
        threats_title: "What Do We Protect Against?",
        threat_1_title: "Vishing Attacks",
        threat_1_desc: "Fraudsters call and ask for bank codes. We instantly alert you.",
        threat_2_title: "Phishing Sites",
        threat_2_desc: "Fake bank and social media sites. The app blocks access to them.",
        threat_3_title: "Malware & Viruses",
        threat_3_desc: "Malicious apps and files. Our AI model detects them before installation.",
        threat_4_title: "Spy Apps",
        threat_4_desc: "Track your messages and location. We find them.",
        // Testimonials
        testimonials_title: "User Reviews",
        test_1_text: "\"Someone called me pretending to be from the bank and asked for an SMS code. Cyber Brother immediately showed a red screen and I wasn't fooled. Thank you!\"",
        test_1_name: "Jasur T.",
        test_1_role: "Tashkent, Entrepreneur",
        test_2_text: "\"I was about to download a game from Play Market, but Cyber Brother said it contained a virus. It was true!\"",
        test_2_name: "Dilnoza A.",
        test_2_role: "Samarkand, Student",
        test_3_text: "\"I installed it on my mom's phone. Now I don't worry about fraudsters calling her.\"",
        test_3_name: "Bekzod R.",
        test_3_role: "Bukhara, Developer"
    }
};

// ===== LANGUAGE SWITCHER =====
function setLanguage(lang) {
    localStorage.setItem('preferredLang', lang);
    const flagMap = { uz: "🇺🇿", ru: "🇷🇺", en: "🇺🇸" };
    const labelMap = { uz: "O'zb", ru: "Рус", en: "Eng" };

    document.getElementById('lang-flag').textContent = flagMap[lang];
    document.getElementById('lang-label').textContent = labelMap[lang];

    document.querySelectorAll('[data-i18n]').forEach(el => {
        const key = el.getAttribute('data-i18n');
        if (translations[lang][key]) {
            el.innerHTML = translations[lang][key];
        }
    });

    document.documentElement.lang = lang;
}

document.querySelectorAll('.lang-option').forEach(option => {
    option.addEventListener('click', () => {
        const lang = option.getAttribute('data-lang');
        setLanguage(lang);
    });
});

// ===== THEME SWITCHER =====
function setTheme(theme) {
    // Remove all theme classes
    document.body.classList.remove('theme-dark', 'theme-ocean', 'theme-forest');

    // Add new theme class (light is default, no class needed)
    if (theme !== 'light') {
        document.body.classList.add('theme-' + theme);
    }

    // Update active state on dots
    document.querySelectorAll('.theme-dot').forEach(dot => {
        dot.classList.remove('active');
        if (dot.getAttribute('data-theme') === theme) {
            dot.classList.add('active');
        }
    });

    // Save preference
    localStorage.setItem('preferredTheme', theme);
}

document.querySelectorAll('.theme-dot').forEach(dot => {
    dot.addEventListener('click', () => {
        const theme = dot.getAttribute('data-theme');
        setTheme(theme);
    });
});

// ===== SCROLL PROGRESS BAR =====
window.addEventListener('scroll', () => {
    const scrollProgress = document.getElementById('scroll-progress');
    const scrollTop = document.documentElement.scrollTop;
    const scrollHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    const scrollPercent = (scrollTop / scrollHeight) * 100;
    scrollProgress.style.width = scrollPercent + '%';

    // Header scroll effect
    const header = document.querySelector('.header');
    if (scrollTop > 50) {
        header.classList.add('scrolled');
    } else {
        header.classList.remove('scrolled');
    }
});

// ===== MOBILE MENU TOGGLE =====
const menuToggle = document.getElementById('menu-toggle');
const mobileNav = document.getElementById('mobile-nav');

if (menuToggle && mobileNav) {
    menuToggle.addEventListener('click', () => {
        mobileNav.classList.toggle('open');
    });

    mobileNav.querySelectorAll('a').forEach(link => {
        link.addEventListener('click', () => {
            mobileNav.classList.remove('open');
        });
    });
}

// ===== SCROLL REVEAL ANIMATION =====
const observerOptions = {
    threshold: 0.1,
    rootMargin: "0px 0px -80px 0px"
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('visible');
        }
    });
}, observerOptions);

document.querySelectorAll('.feature-card, .animate-on-scroll, .dl-card, .contact-card, .about-content, .about-visual').forEach(el => {
    observer.observe(el);
});

// ===== SMOOTH SCROLL WITH OFFSET =====
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const targetId = this.getAttribute('href');
        const target = document.querySelector(targetId);

        if (target) {
            const headerOffset = 100;
            const elementPosition = target.getBoundingClientRect().top;
            const offsetPosition = elementPosition + window.pageYOffset - headerOffset;

            window.scrollTo({
                top: offsetPosition,
                behavior: "smooth"
            });
        }
    });
});

// ===== INIT ON DOM LOAD =====
window.addEventListener('DOMContentLoaded', () => {
    // Load saved language
    const savedLang = localStorage.getItem('preferredLang') || 'uz';
    setLanguage(savedLang);

    // Load saved theme
    const savedTheme = localStorage.getItem('preferredTheme') || 'light';
    setTheme(savedTheme);

    // Track page visit
    trackVisit();
});

// ===== TRACKING FUNCTIONS =====
function trackVisit() {
    const visitData = {
        timestamp: new Date().toISOString(),
        userAgent: navigator.userAgent,
        language: navigator.language,
        platform: navigator.platform,
        type: 'visit'
    };
    saveTrackingData(visitData);
}

function trackDownload(version) {
    const downloadData = {
        timestamp: new Date().toISOString(),
        userAgent: navigator.userAgent,
        language: navigator.language,
        platform: navigator.platform,
        version: version,
        type: 'download'
    };
    saveTrackingData(downloadData);
}

// Automatically detects the current domain for API calls (Render or Local)
const API_BASE_URL = 'https://cyberbrother.onrender.com';

async function saveTrackingData(data) {
    // 1. Save locally (backup)
    let trackingData = JSON.parse(localStorage.getItem('cyberBrotherTracking') || '[]');
    trackingData.push(data);
    localStorage.setItem('cyberBrotherTracking', JSON.stringify(trackingData));

    // 2. Send to Server (Real-time analytics)
    try {
        await fetch(`${API_BASE_URL}/track`, {
            method: 'POST',
            keepalive: true, // Sahifa yopilganda ham yuborish
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
    } catch (e) {
        console.warn('Tracking API unavailable, data saved locally only.');
    }
}

// Secure Download Handling
document.querySelectorAll('.bot-download-link').forEach(link => {
    link.addEventListener('click', async (e) => {
        // We still want tracking, but let's be more reliable with redirection
        trackDownload('v1.3.0');

        // If the API is slow, we don't want the user to wait for a blank page
        // Let's just let the default 'a' tag behavior happen if target is set, 
        // OR manually redirect. Since HTML has href="https://t.me/...", we can just let it go.

        const originalText = link.innerHTML;
        link.innerHTML = '⏳...';

        try {
            const res = await fetch(`${API_BASE_URL}/download`, { signal: AbortSignal.timeout(2000) });
            if (res.ok) {
                const data = await res.json();
                if (data.url) {
                    window.location.href = data.url;
                }
            }
        } catch (e) {
            console.warn("API redirect failed, using default href");
        } finally {
            // If the fetch fails or is slow, the user is still on the page or 
            // the default link action (if we didn't preventDefault) would have worked.
            // But we used preventDefault, so let's force the redirect now.
            setTimeout(() => {
                window.location.href = 'https://t.me/cyberbrotherrobot';
            }, 100);
        }
    });
});
// Feedback Form Handling
const feedbackForm = document.getElementById('feedback-form');
if (feedbackForm) {
    feedbackForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const name = document.getElementById('fb-name').value;
        const message = document.getElementById('fb-message').value;
        const submitBtn = feedbackForm.querySelector('button');
        const originalBtnText = submitBtn.innerHTML;

        // Show loading state
        submitBtn.disabled = true;
        submitBtn.innerHTML = '⏳ Yuborilmoqda...';

        try {
            // Send to our Secure Server API (Token stays on server)
            const response = await fetch(`${API_BASE_URL}/feedback`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, message })
            });

            if (response.ok) {
                // UI Success
                feedbackForm.reset();
                feedbackForm.style.display = 'none';
                document.getElementById('fb-success').style.display = 'block';
            } else {
                const errorData = await response.json().catch(() => ({}));
                throw new Error(errorData.error || 'Server API Error');
            }
        } catch (error) {
            console.error('Feedback error:', error);
            alert(`Xatolik yuz berdi: ${error.message}. Iltimos keyinroq urinib ko'ring.`);
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalBtnText;
        }
    });
}
