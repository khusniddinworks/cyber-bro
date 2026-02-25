# Deploy Version: 2026.02.04.1419 (Force Sync IDs)
import json
import os
import time
import hashlib
import asyncio
import threading
import urllib.request
from datetime import datetime
from http.server import HTTPServer, BaseHTTPRequestHandler
from dotenv import load_dotenv

# Load environment variables from .env file
load_dotenv()

try:
    from telegram import Update, InlineKeyboardButton, InlineKeyboardMarkup, ReplyKeyboardMarkup, KeyboardButton
    from telegram.ext import Application, CommandHandler, CallbackQueryHandler, ContextTypes, MessageHandler, filters
except ImportError:
    print("Xatolik: 'python-telegram-bot' kutubxonasi o'rnatilmagan. 'pip install python-telegram-bot' buyrug'ini ishga tushiring.")
    exit(1)

# Bot Token (Environment variable)
TOKEN = os.getenv('BOT_TOKEN')

# Admin IDs
admin_env = os.getenv('ADMIN_IDS')
if admin_env:
    ADMIN_IDS = [int(i.strip()) for i in admin_env.split(',')]
else:
    ADMIN_IDS = [8332161047] # Default Admin ID

# Admin Password (for Website Admin Panel)
ADMIN_PASSWORD = os.getenv('ADMIN_PASSWORD', 'admin123')
CARD_NUMBER = os.getenv('CARD_NUMBER', 'Bog\'laning')
CARD_NAME = os.getenv('CARD_NAME', '')

# Files Configuration
CONFIG_FILE = 'bot_config.json'
USERS_FILE = 'users.json'
FEEDBACKS_FILE = 'feedbacks.json'
SUBSCRIPTIONS_FILE = 'subscriptions.json'
STATS_FILE = 'stats.json'
WEBSITE_EVENTS_FILE = 'website_events.json'
VERIFIED_DEVICES_FILE = 'verified_devices.json'

# --- DATA LOADING HELPERS ---
def load_config():
    default_config = {
        'v1.7.0': {
            'file_id': 'BQACAgIAAxkBAAIE62mKvrcRynbcMzhRHqf8VZYuwvJsAAIewAACoSNZSGyGRrCl8udmOgQ', 
            'path': 'CyberBrother_PRO_v1.7.0.apk', 
            'name': 'CyberBrother_PRO_v1.7.0.apk'
        },
        'v1.3.0': {
            'file_id': 'BQACAgIAAxkBAAIEKWmEEpl78NuJYSYrHsMBbS6BHVaFAAIHnQACD3YgSDOXojBCJH5VOAQ', 
            'path': 'cyber-brother-v1.3.0.apk', 
            'name': 'CyberBrother_PRO_v1.3.0.apk'
        }
    }
    
    if os.path.exists(CONFIG_FILE):
        with open(CONFIG_FILE, 'r', encoding='utf-8') as f:
            loaded = json.load(f)
            # Merge with default ensuring new version exists
            for key, val in default_config.items():
                if key not in loaded:
                    loaded[key] = val
            return loaded
    return default_config

def save_config(config):
    with open(CONFIG_FILE, 'w', encoding='utf-8') as f:
        json.dump(config, f, indent=4)
    # Background push to keep data synced
    threading.Thread(target=push_to_github, args=([CONFIG_FILE],), daemon=True).start()

def load_users():
    if os.path.exists(USERS_FILE):
        with open(USERS_FILE, 'r', encoding='utf-8') as f:
            return json.load(f)
    return []

def save_users(users):
    with open(USERS_FILE, 'w', encoding='utf-8') as f:
        json.dump(users, f, indent=4, ensure_ascii=False)

def load_subscriptions():
    if os.path.exists(SUBSCRIPTIONS_FILE):
        with open(SUBSCRIPTIONS_FILE, 'r', encoding='utf-8') as f:
            return json.load(f)
    return {}

def save_subscriptions(subs):
    with open(SUBSCRIPTIONS_FILE, 'w', encoding='utf-8') as f:
        json.dump(subs, f, indent=4)

def load_stats():
    if os.path.exists(STATS_FILE):
        with open(STATS_FILE, 'r', encoding='utf-8') as f:
            return json.load(f)
    return {'total_visits': 0, 'total_downloads': 0, 'total_feedbacks': 0}

def save_stats(stats):
    with open(STATS_FILE, 'w', encoding='utf-8') as f:
        json.dump(stats, f, indent=4)

def load_website_events():
    if os.path.exists(WEBSITE_EVENTS_FILE):
         with open(WEBSITE_EVENTS_FILE, 'r', encoding='utf-8') as f:
            return json.load(f)
    return []

# Default slots are now loaded from slots.json to keep code clean
SLOTS_FILE = 'slots.json'

def load_default_slots():
    if os.path.exists(SLOTS_FILE):
        with open(SLOTS_FILE, 'r', encoding='utf-8') as f:
            return json.load(f)
    return []

def load_verified_devices():
    devices = {}
    if os.path.exists(VERIFIED_DEVICES_FILE):
        with open(VERIFIED_DEVICES_FILE, 'r', encoding='utf-8') as f:
            devices = json.load(f)
    
    # Auto-Initialization: Ensure all default slots exist
    updated = False
    default_slots = load_default_slots()
    for slot in default_slots:
        if slot not in devices:
            devices[slot] = {"status": "available", "assigned_to": None}
            updated = True
    
    if updated:
        save_verified_devices(devices)
        
    return devices

def save_verified_devices(devices):
    with open(VERIFIED_DEVICES_FILE, 'w', encoding='utf-8') as f:
        json.dump(devices, f, indent=4)
    push_to_github([VERIFIED_DEVICES_FILE])

def save_website_event(event):
    events = load_website_events()
    events.append(event)
    
    # Update cumulative stats
    stats = load_stats()
    if event.get('type') == 'visit':
        stats['total_visits'] += 1
    elif event.get('type') == 'download':
        stats['total_downloads'] += 1
    elif event.get('type') == 'feedback':
        stats['total_feedbacks'] += 1
    save_stats(stats)

    # Keep a larger history for the admin panel logs
    if len(events) > 5000: events = events[-5000:]
    with open(WEBSITE_EVENTS_FILE, 'w', encoding='utf-8') as f:
        json.dump(events, f, indent=4, ensure_ascii=False)
    
    # Auto-Push to GitHub in background to avoid blocking
    threading.Thread(target=push_to_github, args=([WEBSITE_EVENTS_FILE, STATS_FILE],), daemon=True).start()

def push_to_github(files):
    """Automatically commits and pushes data files to GitHub for persistence."""
    try:
        import subprocess
        if not os.path.exists('.git'): return
        
        for f in files:
            subprocess.run(['git', 'add', f], capture_output=True)
            
        commit_msg = f"chore(data): persist at {datetime.now().strftime('%Y-%m-%d %H:%M')}"
        subprocess.run(['git', 'commit', '-m', commit_msg], capture_output=True)
        subprocess.run(['git', 'push', 'origin', 'main'], capture_output=True)
        print(f"📁 Pushed {len(files)} files to GitHub.")
    except Exception as e:
        print(f"⚠️ Git-Push error: {e}")

def save_users_persistent(users):
    save_users(users)
    push_to_github([USERS_FILE])

def save_subscriptions_persistent(subs):
    save_subscriptions(subs)
    push_to_github([SUBSCRIPTIONS_FILE])

# --- NOTIFICATION HELPER ---
async def notify_admin(context: ContextTypes.DEFAULT_TYPE, message: str):
    """Sends a critical update to all admins immediately."""
    for admin_id in ADMIN_IDS:
        try:
            if context:
                await context.bot.send_message(chat_id=admin_id, text=message, parse_mode='Markdown')
            elif app_instance:
                await app_instance.bot.send_message(chat_id=admin_id, text=message, parse_mode='Markdown')
        except Exception as e:
            print(f"❌ Failed to notify admin {admin_id}: {e}")
            # Log the error to console for debugging

# --- LICENSE LOGIC ---
def generate_license_key(device_id):
    salt = "CYBER_BROTHER_SECRET_2026"
    raw = device_id + salt
    return hashlib.sha256(raw.encode()).hexdigest()[:16].upper()

# --- CONTENT CONSTANTS ---
APK_FILES = {
    'v1.9.0': {
        'label': '🦅 v1.9.0 SUPER ULTRA (Yangi)',
        'caption': (
            "🛡️ *Cyber Brother v1.9.0 ULTRA*\n\n"
            "🔥 *ENG SO'NGGI VERSIYA:* \n"
            "• 🛑 *Full Protection:* Maksimal xavfsizlik protokollari.\n"
            "• 🎯 *Better Detection:* AI model yangilandi.\n"
            "• 🧠 *Ultra Speed:* DEX tahlili tezlashtirildi.\n\n"
            "✅ *Hozirgi eng kuchli va barqaror versiya.*"
        )
    },
    'v1.7.0': {
        'label': '🚀 v1.7.0 PRO',
        'caption': "🛡️ *Cyber Brother v1.7.0 PRO*"
    }
}

# --- KEYBOARDS ---
def get_main_keyboard(is_admin=False):
    keyboard = [
        [KeyboardButton("🛒 Premium Olish"), KeyboardButton("📥 APK Yuklab Olish")],
        [KeyboardButton("🛡️ 100% Himoya yo'riqnomasi"), KeyboardButton("🌐 Vebsayt")],
        [KeyboardButton("ℹ️ Ilova haqida"), KeyboardButton("✍️ Fikr qoldirish")]
    ]
    if is_admin:
        # Add Admin Controls
        keyboard.insert(2, [KeyboardButton("📊 Statistika"), KeyboardButton("📂 Baza Yuklash")])
        
    return ReplyKeyboardMarkup(keyboard, resize_keyboard=True)

# --- HANDLERS ---
async def start(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    context.user_data.clear()
    user = update.effective_user
    users = load_users()
    
    # Check if new user
    if not any(u['id'] == user.id for u in users):
        new_user = {
            'id': user.id, 
            'username': user.username or 'Noma\'lum', 
            'timestamp': datetime.now().isoformat()
        }
        users.append(new_user)
        save_users(users)
        # NOTIFY ADMIN IMMEDIATELY
        await notify_admin(context, f"🆕 *Yangi Foydalanuvchi!*\n👤 @{user.username}\n🆔 `{user.id}`")

    is_admin = user.id in ADMIN_IDS
    
    if is_admin:
        welcome_text = (
            "👑 *Xush kelibsiz, Boss!*\n\n"
            "Siz Cyber Brother PRO tizimining adminisiz.\n"
            "• APK fayl yuborib bazani yangilashingiz mumkin.\n"
            "• /broadcast — Xabar tarqatish\n"
            "• /stats — Statistika"
        )
    else:
        welcome_text = (
            "🦅 *Cyber Brother: Raqamli Dunyodagi Shaxsiy Tansoqchingiz*\n\n"
            "Hush kelibsiz! Jamoamiz sizga *\"Sizning hayotingiz faqat sizga tegishli!\"* degan kafolatni beradi.\n\n"
            "🛡️ *Nega aynan biz?*\n"
            "✅ **100% Offline** — Ma'lumotlaringiz telefondan chiqmaydi.\n"
            "✅ **Mahalliy AI** — O'zbek tilini va firibgarlarni taniydi.\n"
            "✅ **Tezkor va Yengil** — Batareyangizni asraydi.\n\n"
            "👇 *Imkoniyatlardan foydalanish uchun menyuni tanlang:*"
        )
    
    await update.message.reply_text(
        welcome_text, 
        parse_mode='Markdown', 
        reply_markup=get_main_keyboard(is_admin)
    )

async def handle_text_menu(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    text = update.message.text
    user = update.effective_user
    is_admin = user.id in ADMIN_IDS

    # --- ADMIN CONTROLS ---
    if is_admin:
        if text == "📊 Statistika":
            users = load_users()
            subs = load_subscriptions()
            active_subs = sum(1 for s in subs.values() if s['status'] == 'active')
            count = len(users)
            
            feedbacks_count = 0
            if os.path.exists(FEEDBACKS_FILE):
                 with open(FEEDBACKS_FILE, 'r', encoding='utf-8') as f:
                    feedbacks_count = len(json.load(f))
            
            stats_msg = (
                f"📊 *Cyber Brother Statistics*\n\n"
                f"👥 Jami Foydalanuvchilar: `{count}`\n"
                f"💎 Premium Obunalar: `{active_subs}`\n"
                f"📝 Fikrlar: `{feedbacks_count}`\n"
            )
            await update.message.reply_text(stats_msg, parse_mode='Markdown')
            return
        elif text == "📂 Baza Yuklash":
            await update.message.reply_text("📂 Fayllar yuklanmoqda...")
            files = [USERS_FILE, FEEDBACKS_FILE, SUBSCRIPTIONS_FILE]
            found = False
            for filename in files:
                if os.path.exists(filename):
                    found = True
                    with open(filename, 'rb') as f:
                        await update.message.reply_document(document=f, caption=f"📂 {filename}")
            if not found:
                 await update.message.reply_text("⚠️ Hozircha baza bo'sh.")
            return

    # --- 1. PRIORITY BUTTONS (Always work) ---
    if text == "🛒 Premium Olish":
        premium_text = (
            "💎 *Cyber Brother PREMIUM — Cheklovsiz Himoya*\n\n"
            "Siz oddiy foydalanuvchi emassiz. Sizga eng yaxshisi kerak.\n\n"
            "✅ *Anti-Sideloading Shield* (APK o'rnatishni bloklash)\n"
            "✅ *AI Deep Scan* (200% DEX tahlili)\n"
            "✅ *Red Alert Protocol* (Xavfli ilovalarni majburiy o'chirish)\n"
            "✅ *Offline AI Engine* (Maxfiylik kafolati)\n\n"
            "💰 *Narxi: 10,000 so'm / oy* (14 kunlik bepul sinov)\n"
            "Muddat: Cheksiz yangilanishlar."
        )
        keyboard = [[InlineKeyboardButton("💳 To'lov qilish", callback_data='pay_premium')]]
        await update.message.reply_text(premium_text, parse_mode='Markdown', reply_markup=InlineKeyboardMarkup(keyboard))
        return

    if text == "📥 APK Yuklab Olish":
        version_text = "📱 *Cyber Brother ULTRA v1.9.0*\n\nEng so'nggi va eng xavfsiz versiyani yuklab oling:"
        keyboard = [
            [InlineKeyboardButton(APK_FILES['v1.9.0']['label'], callback_data='download_v1.9.0')]
        ]
        await update.message.reply_text(version_text, parse_mode='Markdown', reply_markup=InlineKeyboardMarkup(keyboard))
        return

    if text == "🛡️ 100% Himoya yo'riqnomasi" or text == "/help_protect":
        guide_text = (
            "🛡️ *Cyber Brother: 100% Himoyani Yoqish Yo'riqnomasi*\n\n"
            "Tizim to'liq ishlashi uchun quyidagi 10 ta qadamni bajaring:\n\n"
            "1️⃣ *Fayllarga ruxsat:* Ilova ichida 'Barcha fayllarni boshqarish'ni yoqing.\n"
            "2️⃣ *Dashboard:* 'Xavfli fayllar filtri' va boshqa ruxsatlarni tasdiqlang.\n"
            "3️⃣ *Bildirishnomalar:* Cyber Brotherga bildirishnomalarga kirish ruxsatini bering.\n"
            "4️⃣ *Sozlamalar:* Telefoningizning 'Sozlamalar' (Settings) menyusiga kiring.\n"
            "5️⃣ *Ilovalar:* 'Ilova boshqaruvi' bo'limini tanlang.\n"
            "6️⃣ *Ilova tanlash:* Ro'yxatdan 'Cyber Brother'ni toping.\n"
            "7️⃣ *3 nuqta:* Yuqori o'ng burchakdagi 3 ta nuqta tugmasini bosing.\n"
            "8️⃣ *Cheklov yechish:* 'Cheklangan sozlamalarga ruxsat berish' bandini tanlang.\n"
            "9️⃣ *Qaytish:* Ilovaga qaytib, 'Fishingdan himoya' tugmasini bosing.\n"
            "🔟 *So'nggi qadam:* 'Accessibility' bo'limidan 'Cyber Brother Phishing Protection'ni yoqing.\n\n"
            "✅ *Bajarildi! Endi qurilmangiz mutlaq xavfsiz.*"
        )
        await update.message.reply_text(guide_text, parse_mode='Markdown')
        return

    if text == "🌐 Vebsayt":
        await update.message.reply_text("🔗 [https://khusniddinworks.github.io/cyber-bro/](https://khusniddinworks.github.io/cyber-bro/)")
        return

    if text == "ℹ️ Ilova haqida":
        about_text = (
            "ℹ️ *Cyber Brother PRO Haqida*\n\n"
            "Biz jahon gigantlariga (Kaspersky, Bitdefender) qarshi yangi muqobilmiz.\n\n"
            "1️⃣ *100% Offline Maxfiylik:* Server yo'q. Bizda sizning ma'lumotlaringizni ko'rish imkoni yo'q.\n"
            "2️⃣ *Mahalliy AI:* O'zbek tilida so'zlashuvchi va mahalliy muammolarni tushunuvchi yagona kiber-aql.\n"
            "3️⃣ *Resurs Tejamkorligi:* Telefoningizni qizdirmaydi va quvvatini yemaydi.\n\n"
            "🦅 *Bizning shior:* Sizning xavfsizligingiz — bizning obro'yimiz!"
        )
        await update.message.reply_text(about_text, parse_mode='Markdown')
        return

    if text == "✍️ Fikr qoldirish":
        context.user_data['state'] = 'waiting_feedback'
        await update.message.reply_text("📝 Fikringizni yozing:", reply_markup=ReplyKeyboardMarkup([[KeyboardButton("🚫 Bekor qilish")]], resize_keyboard=True))
        return

    # --- 2. STATE-BASED FLOWS (Inputs) ---
    state = context.user_data.get('state')
    
    if state == 'waiting_device_id':
        if text == "🚫 Bekor qilish":
            context.user_data.clear()
            await update.message.reply_text("❌ Bekor qilindi.", reply_markup=get_main_keyboard(is_admin))
            return
            
        device_id = text.strip().upper()
        verified_devices = load_verified_devices()
        
        if device_id not in verified_devices:
            await update.message.reply_text(
                "❌ *Xatolik: Ushbu Device ID tizimda mavjud emas!*\n\n"
                "Iltimos, avval premium sotib oling yoki admin bilan bog'laning.",
                parse_mode='Markdown'
            )
            return

        if verified_devices[device_id]['status'] == 'active':
             await update.message.reply_text(
                f"✅ *Sizning kalitingiz allaqachon mavjud:*\n\n`{verified_devices[device_id]['key']}`",
                parse_mode='Markdown',
                reply_markup=get_main_keyboard(is_admin)
            )
             context.user_data.clear()
             return

        license_key = generate_license_key(device_id)
        verified_devices[device_id]['status'] = 'active'
        verified_devices[device_id]['assigned_to'] = user.id
        verified_devices[device_id]['key'] = license_key
        verified_devices[device_id]['activated_at'] = datetime.now().isoformat()
        save_verified_devices(verified_devices)
        
        subs = load_subscriptions()
        subs[device_id] = {'user_id': user.id, 'status': 'active', 'key': license_key}
        save_subscriptions(subs)
        
        await notify_admin(context, f"💰 *Yangi Aktivatsiya!*\nUser: @{user.username}\nID: `{device_id}`")
        context.user_data.clear()
        await update.message.reply_text(
            f"✅ *Tabriklaymiz!*\n\n🔑 Kalitingiz: `{license_key}`\n\nIlovaga kiriting.", 
            parse_mode='Markdown', 
            reply_markup=get_main_keyboard(is_admin)
        )
        return

    if state == 'waiting_feedback':
        if text == "🚫 Bekor qilish":
            context.user_data.clear()
            await update.message.reply_text("❌ Bekor qilindi.", reply_markup=get_main_keyboard(is_admin))
            return
            
        feedbacks = []
        if os.path.exists(FEEDBACKS_FILE):
             with open(FEEDBACKS_FILE, 'r', encoding='utf-8') as f:
                feedbacks = json.load(f)
        
        new_feedback = {
            'user_id': user.id,
            'username': user.username,
            'text': text,
            'timestamp': datetime.now().isoformat()
        }
        feedbacks.append(new_feedback)
        
        with open(FEEDBACKS_FILE, 'w', encoding='utf-8') as f:
            json.dump(feedbacks, f, indent=4, ensure_ascii=False)
            
        await notify_admin(context, f"📩 *Yangi Fikr (Feedback)*\n👤 Kimdan: @{user.username}\n🆔 ID: `{user.id}`\n💬 Xabar: {text}\n\nJavob berish uchun: `/reply {user.id} [xabar]`")
        context.user_data.clear()
        await update.message.reply_text("Rahmat! Fikringiz adminga yetkazildi.", reply_markup=get_main_keyboard(is_admin))
        return

async def download_callback(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    query = update.callback_query
    version = query.data.replace('download_', '')
    config = load_config()

    if version not in APK_FILES: return
    await query.answer("⏳ Fayl yuklanmoqda... (Biroz kuting)")
    
    # Check if we have file_id
    file_id = config.get(version, {}).get('file_id')
    path = config.get(version, {}).get('path')
    
    try:
        if file_id:
            # Send by ID (Fast)
            await query.message.reply_document(
                document=file_id,
                filename=config[version]['name'],
                caption=APK_FILES[version]['caption'],
                parse_mode='Markdown'
            )
        elif path and os.path.exists(path):
            # Send by Uploading File (First time)
            msg = await query.message.reply_text("📤 Fayl serverga yuklanmoqda... (Bu bir marta bo'ladi)")
            with open(path, 'rb') as f:
                sent_msg = await query.message.reply_document(
                    document=f, 
                    filename=config[version]['name'], 
                    caption=APK_FILES[version]['caption'], 
                    parse_mode='Markdown'
                )
                
                # Capture and Save File ID for next time
                new_file_id = sent_msg.document.file_id
                config[version]['file_id'] = new_file_id
                save_config(config)
                
                await msg.delete() # Remove 'uploading' message
        else:
            await query.message.reply_text("❌ Fayl serverda topilmadi. Admin bilan bog'laning.")

        # Update Last Download Stats
        users = load_users()
        for u in users:
            if u['id'] == query.from_user.id:
                u['last_download'] = version
                break
        save_users(users)
        
    except Exception as e:
        await query.message.reply_text(f"❌ Xatolik yuz berdi: {str(e)}")

async def pay_premium_callback(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    query = update.callback_query
    await query.answer()
    
    pay_text = (
        "💳 *Cyber Brother PREMIUM Obuna*\n\n"
        "💰 *Narxi:* 10,000 so'm / oy\n"
        "🎁 *Sinov davri:* 14 kun bepul\n"
        "✨ *Imkoniyat:* To'liq AI himoya va Anti-Sideloading\n\n"
        "*To'lov usullari:*\n"
        f"💳 Karta: `{CARD_NUMBER}`\n"
        f"👤 Ega: {CARD_NAME}\n\n"
        "⚠️ *Diqqat:* To'lov qilgach, quyidagi 'To'lovni Tasdiqlash' tugmasini bosing."
    )
    keyboard = [[InlineKeyboardButton("✅ To'lovni Tasdiqlash", callback_data='confirm_payment')]]
    await query.message.reply_text(pay_text, parse_mode='Markdown', reply_markup=InlineKeyboardMarkup(keyboard))

async def confirm_payment_callback(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    query = update.callback_query
    await query.answer()
    
    await query.edit_message_text(
        "🧾 *To'lovni tasdiqlash*\n\n"
        "Iltimos, to'lov chekini (rasm yoki PDF fayl) shu yerga yuboring. Admin tasdiqlashi uchun bu zarur.",
        parse_mode='Markdown'
    )
    context.user_data['state'] = 'waiting_payment_receipt'

async def handle_payment_media(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    user = update.effective_user
    state = context.user_data.get('state')
    
    if state == 'waiting_payment_receipt':
        # Admin Approval Buttons
        keyboard = [
            [
                InlineKeyboardButton("✅ Tasdiqlash", callback_data=f"approve_pay_{user.id}"),
                InlineKeyboardButton("❌ Rad etish", callback_data=f"reject_pay_{user.id}")
            ]
        ]
        reply_markup = InlineKeyboardMarkup(keyboard)

        # Forward to all admins
        for admin_id in ADMIN_IDS:
            try:
                caption = (
                    f"🧾 *YANGI TO'LOV CHEKI*\n\n"
                    f"👤 Foydalanuvchi: @{user.username}\n"
                    f"🆔 ID: `{user.id}`\n\n"
                    f"Tasdiqlash tugmasini bossangiz, foydalanuvchidan Device ID so'raladi."
                )
                if update.message.photo:
                    await context.bot.send_photo(chat_id=admin_id, photo=update.message.photo[-1].file_id, caption=caption, parse_mode='Markdown', reply_markup=reply_markup)
                elif update.message.document:
                    await context.bot.send_document(chat_id=admin_id, document=update.message.document.file_id, caption=caption, parse_mode='Markdown', reply_markup=reply_markup)
            except Exception as e:
                print(f"Error forwarding receipt: {e}")
        
        await update.message.reply_text(
            "✅ *Chek adminga yuborildi!*\n\n"
            "To'lov tekshirilmoqda. Admin tasdiqlashi bilan sizdan **Device ID** so'raladi. Iltimos, biroz kuting. ⏳",
            parse_mode='Markdown'
        )
        context.user_data['state'] = None # Wait for admin action
        return
    
    # APK Upload logic remains for admin
    if user.id in ADMIN_IDS and update.message.document:
        await handle_document(update, context)

async def approve_payment_callback(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    query = update.callback_query
    admin_id = query.from_user.id
    if admin_id not in ADMIN_IDS: return
    
    data = query.data
    target_user_id = int(data.split('_')[-1])
    
    await query.answer()
    
    if data.startswith('approve_pay_'):
        # 1. Set user state remotely
        if target_user_id not in context.application.user_data:
            context.application.user_data[target_user_id] = {}
        context.application.user_data[target_user_id]['state'] = 'waiting_device_id'

        # 2. Notify User
        try:
            msg = (
                "🎉 *To'lovingiz tasdiqlandi!*\n\n"
                "Endi ilovangizdagi **Device ID**ni yuboring. Biz sizga aktivatsiya kalitini generatsiya qilib beramiz."
            )
            await context.bot.send_message(chat_id=target_user_id, text=msg, parse_mode='Markdown')
        except: pass
        
        await query.edit_message_caption(caption=query.message.caption + "\n\n✅ *TASDIQLANDI*", parse_mode='Markdown')
        
    elif data.startswith('reject_pay_'):
        try:
            msg = "❌ *To'lovingiz rad etildi.*\n\nChek noto'g'ri yoki mablag' kelib tushmadi. Muammo bo'lsa adminga murojaat qiling."
            await context.bot.send_message(chat_id=target_user_id, text=msg, parse_mode='Markdown')
        except: pass
        
        await query.edit_message_caption(caption=query.message.caption + "\n\n❌ *RAD ETILDI*", parse_mode='Markdown')

# --- ADMIN COMMANDS ---
async def broadcast_command(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    if update.effective_user.id not in ADMIN_IDS: return
    msg = update.message.text.replace('/broadcast', '').strip()
    if not msg:
        await update.message.reply_text("Matn kiriting!")
        return
        
    users = load_users()
    count = 0
    await update.message.reply_text(f"📡 Sending to {len(users)} users...")
    for u in users:
        try:
            await context.bot.send_message(chat_id=u['id'], text=f"📢 *RASMIY XABAR*\n\n{msg}", parse_mode='Markdown')
            count += 1
            await asyncio.sleep(0.05)
        except: pass
    await update.message.reply_text(f"✅ Sent to {count} users.")

async def admin_command(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    if update.effective_user.id not in ADMIN_IDS: return
    await update.message.reply_text("Admin Tools:\n/broadcast <msg>\n/reply <id> <msg>\n/stats\n/export\nDB Auto-Wipe Guard Active (Forwarding enabled)")

async def stats_command(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    if update.effective_user.id not in ADMIN_IDS: return
    users = load_users()
    verified = load_verified_devices()
    active_premium = sum(1 for d in verified.values() if d.get('status') == 'active')
    
    msg = (
        "📊 *Cyber Brother Statistika*\n\n"
        f"👥 Foydalanuvchilar: {len(users)}\n"
        f"💎 Premium (Faol): {active_premium}\n"
        f"🎫 Baza slotlari: {len(verified)}\n"
    )
    await update.message.reply_text(msg, parse_mode='Markdown')

async def add_id_command(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    """Admin tool to register a physical ID."""
    if update.effective_user.id not in ADMIN_IDS: return
    if not context.args:
        await update.message.reply_text("Foydalanish: `/add_id XXXX-XXXX-XXXX`", parse_mode='Markdown')
        return
    
    new_id = context.args[0].upper()
    verified = load_verified_devices()
    if new_id in verified:
        await update.message.reply_text("⚠️ Bu ID bazada bor.")
        return
        
    verified[new_id] = {"status": "available", "assigned_to": None}
    save_verified_devices(verified)
    await update.message.reply_text(f"✅ ID bazaga qo'shildi: `{new_id}`")

async def export_command(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    if update.effective_user.id not in ADMIN_IDS: return
    for f in [USERS_FILE, FEEDBACKS_FILE, SUBSCRIPTIONS_FILE]:
        if os.path.exists(f): 
            with open(f, 'rb') as file: await update.message.reply_document(file)

async def reply_command(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    """Allows admin to reply to a user feedback."""
    if update.effective_user.id not in ADMIN_IDS: return
    
    # Format: /reply [user_id] [message]
    args = context.args
    if len(args) < 2:
        await update.message.reply_text("⚠️ Foydalanish: `/reply [user_id] [xabar]`", parse_mode='Markdown')
        return
        
    try:
        target_user_id = int(args[0])
        reply_msg = " ".join(args[1:])
        
        admin_reply = (
            "🛠️ *TEXNIK YORDAM (Javob)*\n\n"
            f"{reply_msg}\n\n"
            "🛡️ _Cyber Brother Security Team_"
        )
        
        await context.bot.send_message(chat_id=target_user_id, text=admin_reply, parse_mode='Markdown')
        await update.message.reply_text(f"✅ Xabar yuborildi (ID: {target_user_id})")
    except Exception as e:
        await update.message.reply_text(f"❌ Xatolik: {e}")

async def handle_document(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    if update.effective_user.id not in ADMIN_IDS: return
    file_id = update.message.document.file_id
    context.user_data['temp_file_id'] = file_id
    keyboard = [[InlineKeyboardButton("🚀 v1.3.0 PRO", callback_data='save_v1.3.0')], [InlineKeyboardButton("📦 v1.2.2 CORE", callback_data='save_v1.2.2')]]
    await update.message.reply_text(f"ID: `{file_id}`. Save as?", reply_markup=InlineKeyboardMarkup(keyboard), parse_mode='Markdown')

async def save_version_callback(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    if update.callback_query.from_user.id not in ADMIN_IDS: return
    version = update.callback_query.data.replace('save_', '')
    file_id = context.user_data.get('temp_file_id')
    if file_id:
        config = load_config()
        if version not in config:
            config[version] = {
                'file_id': file_id,
                'path': f'cyber-brother-{version}.apk',
                'name': f'CyberBrother_{version}.apk'
            }
        else:
            config[version]['file_id'] = file_id
            
        save_config(config)
        await update.callback_query.message.edit_text(f"✅ {version} updated and saved!")

# --- SERVER & MAIN ---
app_instance = None
loop_instance = None
PENDING_LOGIN_CODES = {}

async def process_telegram_update(data):
    if app_instance:
        try:
            update = Update.de_json(json.loads(data), app_instance.bot)
            await app_instance.process_update(update)
        except: pass

class UniversalServer(BaseHTTPRequestHandler):
    def _set_headers(self, status=200):
        self.send_response(status)
        self.send_header('Content-type', 'application/json')
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS')
        self.send_header('Access-Control-Allow-Headers', 'Content-Type, X-Admin-Password')
        self.end_headers()

    def do_OPTIONS(self):
        self._set_headers(200)

    def do_POST(self):
        content_length = int(self.headers.get('Content-Length', 0))
        post_data = self.rfile.read(content_length)

        if self.path == '/telegram-webhook':
            self._set_headers(200)
            self.wfile.write(b'OK')
            if loop_instance and app_instance:
                asyncio.run_coroutine_threadsafe(process_telegram_update(post_data), loop_instance)
            return
        
        if self.path == '/admin-login':
            try:
                import random
                data = json.loads(post_data)
                password = data.get('password')
                code = data.get('code')
                
                if password == ADMIN_PASSWORD:
                    # 2FA LOGIC
                    if not code:
                        # 1. Generate & Send Code
                        otp = str(random.randint(100000, 999999))
                        PENDING_LOGIN_CODES['admin'] = {'code': otp, 'timestamp': time.time()}
                        
                        msg = f"🔐 *Admin Panel Login*\n\nTasdiqlash kodi: `{otp}`\n(2 daqiqa amal qiladi)"
                        if loop_instance and app_instance:
                             print(f"🔐 Generating 2FA for admin. Code: {otp}")
                             asyncio.run_coroutine_threadsafe(notify_admin(None, msg), loop_instance)

                        self._set_headers(200)
                        self.wfile.write(b'{"status":"2fa_required"}')
                    else:
                        # 2. Verify Code
                        record = PENDING_LOGIN_CODES.get('admin')
                        if record and record['code'] == code and (time.time() - record['timestamp'] < 120):
                            del PENDING_LOGIN_CODES['admin']
                            self._set_headers(200)
                            self.wfile.write(b'{"status":"ok"}')
                        else:
                            self._set_headers(401)
                            self.wfile.write(b'{"error":"Kod noto\'g\'ri yoki eskirgan!"}')
                else:
                    self._set_headers(401)
                    self.wfile.write(b'{"error":"Unauthorized"}')
            except Exception as e:
                print(e)
                self._set_headers(400)
            return

        if self.path == '/track':
            try: 
                data = json.loads(post_data)
                
                # --- CAPTURE IP ADDRESS ---
                # Check for proxy header (Render.com uses this)
                forwarded_for = self.headers.get('X-Forwarded-For')
                if forwarded_for:
                    # The first IP in the list is the real client IP
                    client_ip = forwarded_for.split(',')[0].strip()
                else:
                    # Fallback to direct connection IP
                    client_ip = self.client_address[0]
                
                data['ip'] = client_ip
                # --------------------------

                save_website_event(data)
            except: pass
            self._set_headers()
            self.wfile.write(b'{"status":"ok"}')
            return

        if self.path == '/feedback':
            try:
                if not post_data:
                    raise ValueError("Empty request body")
                data = json.loads(post_data.decode('utf-8'))
                fbs = []
                if os.path.exists(FEEDBACKS_FILE) and os.path.getsize(FEEDBACKS_FILE) > 0:
                    try:
                        with open(FEEDBACKS_FILE, 'r', encoding='utf-8') as f:
                            fbs = json.load(f)
                    except: fbs = []
                
                fbs.append({
                    'name': data.get('name', 'Anonymous'),
                    'message': data.get('message', ''),
                    'timestamp': datetime.now().isoformat(),
                    'source': 'Website'
                })
                
                with open(FEEDBACKS_FILE, 'w', encoding='utf-8') as f:
                    json.dump(fbs, f, indent=4, ensure_ascii=False)
                
                # Push feedback to GitHub too
                threading.Thread(target=push_to_github, args=([FEEDBACKS_FILE],), daemon=True).start()
                
                self._set_headers()
                self.wfile.write(b'{"status":"ok"}')
                print(f"✅ Feedback received: {data.get('name')}")
            except Exception as e:
                print(f"❌ Feedback Error: {e}")
                self._set_headers(500)
                self.wfile.write(json.dumps({"error": str(e)}).encode())
            return

        self._set_headers(404)

    def do_GET(self):
        # Admin Header Check
        admin_pass = self.headers.get('X-Admin-Password')
        
        if self.path == '/events':
            if admin_pass != ADMIN_PASSWORD:
                self._set_headers(401)
                self.wfile.write(b'{"error":"Unauthorized"}')
                return
            events = load_website_events()
            stats = load_stats()
            self._set_headers()
            self.wfile.write(json.dumps({"events": events, "cumulative": stats}).encode())
            return

        if self.path == '/bot-users':
            if admin_pass != ADMIN_PASSWORD:
                self._set_headers(401)
                self.wfile.write(b'{"error":"Unauthorized"}')
                return
            self._set_headers()
            self.wfile.write(json.dumps(load_users()).encode())
            return
            
        if self.path == '/bot-feedbacks':
            if admin_pass != ADMIN_PASSWORD:
                self._set_headers(401)
                self.wfile.write(b'{"error":"Unauthorized"}')
                return
            fbs = []
            if os.path.exists(FEEDBACKS_FILE):
                with open(FEEDBACKS_FILE, 'r', encoding='utf-8') as f:
                    fbs = json.load(f)
            self._set_headers()
            self.wfile.write(json.dumps(fbs).encode())
            return

        if self.path == '/download':
            self._set_headers()
            self.wfile.write(b'{"url":"https://t.me/cyberbrotherrobot"}')
            return

        self._set_headers()
        self.wfile.write(b'{"status":"online"}')

# --- KEEP ALIVE LOGIC ---
def start_keep_alive(url):
    """Pings the server every 14 minutes to prevent sleep (Limit is 15 mins)."""
    if not url.startswith('http'): url = f"https://{url}"
    print(f"🔄 Keep-Alive Service Started for: {url}")
    
    def pinger():
        while True:
            try:
                # Use a proper User-Agent to avoid being blocked
                req = urllib.request.Request(
                    url, 
                    headers={'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) KeepAlive/1.0'}
                )
                with urllib.request.urlopen(req, timeout=20) as r:
                    print(f"✅ Ping Success: {r.status}")
            except Exception as e:
                print(f"⚠️ Ping Failed: {e}")
            
            # Ping every 14 minutes (840 seconds) to be safe within 15min limit
            # Randomize slightly to avoid predictable patterns if needed, but fixed is fine
            time.sleep(840) 
            
    threading.Thread(target=pinger, daemon=True).start()

def main():
    global app_instance, loop_instance
    application = Application.builder().token(TOKEN).build()
    app_instance = application

    # Handlers
    application.add_handler(CommandHandler("start", start))
    application.add_handler(CommandHandler("broadcast", broadcast_command))
    application.add_handler(CommandHandler("reply", reply_command))
    application.add_handler(CommandHandler("admin", admin_command))
    application.add_handler(CommandHandler("stats", stats_command))
    application.add_handler(CommandHandler("export", export_command))
    application.add_handler(CommandHandler("add_id", add_id_command))
    application.add_handler(CallbackQueryHandler(download_callback, pattern='^download_v'))
    application.add_handler(CallbackQueryHandler(save_version_callback, pattern='^save_v'))
    application.add_handler(CallbackQueryHandler(pay_premium_callback, pattern='^pay_premium'))
    application.add_handler(CallbackQueryHandler(confirm_payment_callback, pattern='^confirm_payment'))
    application.add_handler(CallbackQueryHandler(approve_payment_callback, pattern='^(approve|reject)_pay_'))
    application.add_handler(MessageHandler(filters.TEXT & ~filters.COMMAND, handle_text_menu))
    application.add_handler(MessageHandler(filters.PHOTO | filters.Document.ALL, handle_payment_media))

    RENDER_URL = os.getenv('RENDER_EXTERNAL_URL')
    PORT = int(os.environ.get("PORT", 8080))
    
    loop = asyncio.new_event_loop()
    asyncio.set_event_loop(loop)
    loop_instance = loop

    if RENDER_URL:
        print(f"🚀 WEBHOOK MODE: {RENDER_URL}")
        
        # Start the robust Keep-Alive Pinger
        start_keep_alive(RENDER_URL)
        
        loop.run_until_complete(application.initialize())
        loop.run_until_complete(application.bot.set_webhook(f"{RENDER_URL}/telegram-webhook"))
        loop.run_until_complete(application.start())
        
        server = HTTPServer(('0.0.0.0', PORT), UniversalServer)
        threading.Thread(target=server.serve_forever, daemon=True).start()
        
        loop.run_forever()
    else:
        print("POLLING MODE (Only Bot, No Local API)")
        application.run_polling()

if __name__ == '__main__':
    main()
