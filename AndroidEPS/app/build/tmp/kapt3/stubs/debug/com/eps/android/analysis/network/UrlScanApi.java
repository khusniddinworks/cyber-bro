package com.eps.android.analysis.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J+\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u00062\b\b\u0001\u0010\u000b\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lcom/eps/android/analysis/network/UrlScanApi;", "", "getResult", "Lretrofit2/Response;", "Lcom/eps/android/analysis/network/UrlScanResultResponse;", "uuid", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitScan", "Lcom/eps/android/analysis/network/UrlScanSubmissionResponse;", "apiKey", "request", "Lcom/eps/android/analysis/network/UrlScanRequest;", "(Ljava/lang/String;Lcom/eps/android/analysis/network/UrlScanRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface UrlScanApi {
    
    @retrofit2.http.POST(value = "scan")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object submitScan(@retrofit2.http.Header(value = "API-Key")
    @org.jetbrains.annotations.NotNull
    java.lang.String apiKey, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.eps.android.analysis.network.UrlScanRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.eps.android.analysis.network.UrlScanSubmissionResponse>> $completion);
    
    @retrofit2.http.GET(value = "result/{uuid}")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getResult(@retrofit2.http.Path(value = "uuid")
    @org.jetbrains.annotations.NotNull
    java.lang.String uuid, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.eps.android.analysis.network.UrlScanResultResponse>> $completion);
}