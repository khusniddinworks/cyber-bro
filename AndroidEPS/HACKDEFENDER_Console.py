import tkinter as tk
import customtkinter as ctk
import subprocess
import threading
import os

ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")

class HackDefenderConsole(ctk.CTk):
    def __init__(self):
        super().__init__()

        self.title("HACKDEFENDER | Monitoring Console")
        self.geometry("900x600")

        # UI Layout
        self.grid_columnconfigure(1, weight=1)
        self.grid_rowconfigure(0, weight=1)

        # Sidebar
        self.sidebar_frame = ctk.CTkFrame(self, width=200, corner_radius=0)
        self.sidebar_frame.grid(row=0, column=0, sticky="nsew")
        
        self.logo_label = ctk.CTkLabel(self.sidebar_frame, text="HACKDEFENDER", font=ctk.CTkFont(size=20, weight="bold"), text_color="#00E5FF")
        self.logo_label.pack(pady=20)

        self.btn_deploy = ctk.CTkButton(self.sidebar_frame, text="DEPLOY & RUN", command=self.deploy_apk, fg_color="#1B5E20", hover_color="#2E7D32")
        self.btn_deploy.pack(pady=10, px=20)

        self.btn_clear = ctk.CTkButton(self.sidebar_frame, text="CLEAR LOGS", command=self.clear_logs)
        self.btn_clear.pack(pady=10, px=20)

        self.status_label = ctk.CTkLabel(self.sidebar_frame, text="Status: Connected", text_color="#00FF41")
        self.status_label.pack(side="bottom", pady=20)

        # Log Terminal
        self.log_frame = ctk.CTkFrame(self, corner_radius=10)
        self.log_frame.grid(row=0, column=1, padx=20, pady=20, sticky="nsew")
        self.log_frame.grid_columnconfigure(0, weight=1)
        self.log_frame.grid_rowconfigure(0, weight=1)

        self.log_textbox = ctk.CTkTextbox(self.log_frame, font=("Consolas", 12), text_color="#00E5FF", border_color="#00E5FF", border_width=1)
        self.log_textbox.grid(row=0, column=0, sticky="nsew", padx=10, pady=10)

        self.append_log(">>> HACKDEFENDER Console V1.0 Ready.")
        self.append_log(">>> Waiting for ADB stream...")

        # Start Log Streaming Thread
        self.streaming = True
        self.log_thread = threading.Thread(target=self.stream_logcat, daemon=True)
        self.log_thread.start()

    def append_log(self, message):
        self.log_textbox.insert("end", message + "\n")
        self.log_textbox.see("end")

    def stream_logcat(self):
        try:
            # Filter specifically for our app logs
            process = subprocess.Popen(
                ["adb", "logcat", "-s", "HACKDEFENDER:I", "TIMBER:I", "EPS_SECURITY:W"],
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                text=True
            )
            for line in process.stdout:
                if not self.streaming: break
                self.after(0, lambda l=line: self.append_log(l.strip()))
        except Exception as e:
            self.after(0, lambda: self.append_log(f"ERROR: {str(e)}"))

    def deploy_apk(self):
        self.append_log(">>> Deploying APK...")
        apk_path = r"c:\Users\ki770\OneDrive\Desktop\New folder\AndroidEPS\app\build\outputs\apk\debug\app-debug.apk"
        
        def run_deploy():
            try:
                # Install
                res = subprocess.run(["adb", "install", "-r", apk_path], capture_output=True, text=True)
                self.after(0, lambda: self.append_log(f">>> {res.stdout.strip()}"))
                
                # Launch
                subprocess.run(["adb", "shell", "am", "start", "-n", "com.eps.android/.MainActivity"], capture_output=True)
                self.after(0, lambda: self.append_log(">>> App Launched Successfully."))
            except Exception as e:
                self.after(0, lambda: self.append_log(f"DEPLOY ERROR: {str(e)}"))

        threading.Thread(target=run_deploy, daemon=True).start()

    def clear_logs(self):
        self.log_textbox.delete("1.0", "end")

if __name__ == "__main__":
    app = HackDefenderConsole()
    app.mainloop()
