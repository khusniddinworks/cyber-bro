import urllib.request
import time
import os
from datetime import datetime

# URL of your Render server
# Example: "https://cyberbrother.onrender.com"
URL = "https://cyberbrother.onrender.com"

def ping():
    print(f"[{datetime.now().strftime('%H:%M:%S')}] Pinging {URL}...")
    try:
        with urllib.request.urlopen(URL) as response:
            status = response.getcode()
            print(f"✅ Success! Status: {status}")
    except Exception as e:
        print(f"❌ Error: {e}")

if __name__ == "__main__":
    print("🚀 Render Keep-Alive script started.")
    print("Press Ctrl+C to stop.")
    while True:
        ping()
        # The user requested 10 seconds
        time.sleep(10)
