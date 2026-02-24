package com.eps.android.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001\u001eB!\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000eH\u0002J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u000e\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u0017R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/eps/android/ui/viewmodel/AiMentorViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "threatEventDao", "Lcom/eps/android/data/ThreatEventDao;", "offlineAi", "Lcom/eps/android/analysis/ai/OfflineAiEngine;", "(Landroid/content/Context;Lcom/eps/android/data/ThreatEventDao;Lcom/eps/android/analysis/ai/OfflineAiEngine;)V", "_chatHistory", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/eps/android/ui/viewmodel/AiMentorViewModel$ChatMessage;", "_isTyping", "", "chatHistory", "Lkotlinx/coroutines/flow/StateFlow;", "getChatHistory", "()Lkotlinx/coroutines/flow/StateFlow;", "isTyping", "addToHistory", "", "text", "", "isUser", "executeSystemAction", "action", "Lcom/eps/android/analysis/ai/OfflineAiEngine$SystemAction;", "sendMessage", "input", "ChatMessage", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class AiMentorViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.data.ThreatEventDao threatEventDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.eps.android.analysis.ai.OfflineAiEngine offlineAi = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.eps.android.ui.viewmodel.AiMentorViewModel.ChatMessage>> _chatHistory = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.eps.android.ui.viewmodel.AiMentorViewModel.ChatMessage>> chatHistory = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isTyping = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isTyping = null;
    
    @javax.inject.Inject
    public AiMentorViewModel(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.eps.android.data.ThreatEventDao threatEventDao, @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.ai.OfflineAiEngine offlineAi) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.eps.android.ui.viewmodel.AiMentorViewModel.ChatMessage>> getChatHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isTyping() {
        return null;
    }
    
    public final void sendMessage(@org.jetbrains.annotations.NotNull
    java.lang.String input) {
    }
    
    private final void executeSystemAction(com.eps.android.analysis.ai.OfflineAiEngine.SystemAction action) {
    }
    
    private final void addToHistory(java.lang.String text, boolean isUser) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000b\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0012"}, d2 = {"Lcom/eps/android/ui/viewmodel/AiMentorViewModel$ChatMessage;", "", "text", "", "isUser", "", "(Ljava/lang/String;Z)V", "()Z", "getText", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class ChatMessage {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String text = null;
        private final boolean isUser = false;
        
        public ChatMessage(@org.jetbrains.annotations.NotNull
        java.lang.String text, boolean isUser) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getText() {
            return null;
        }
        
        public final boolean isUser() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.eps.android.ui.viewmodel.AiMentorViewModel.ChatMessage copy(@org.jetbrains.annotations.NotNull
        java.lang.String text, boolean isUser) {
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