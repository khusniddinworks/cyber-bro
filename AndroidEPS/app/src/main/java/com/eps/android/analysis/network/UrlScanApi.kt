package com.eps.android.analysis.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface UrlScanApi {
    @POST("scan")
    suspend fun submitScan(
        @Header("API-Key") apiKey: String,
        @Body request: UrlScanRequest
    ): Response<UrlScanSubmissionResponse>

    @GET("result/{uuid}")
    suspend fun getResult(
        @Path("uuid") uuid: String
    ): Response<UrlScanResultResponse>
}

data class UrlScanRequest(
    val url: String,
    val visibility: String = "public"
)

data class UrlScanSubmissionResponse(
    val message: String,
    val uuid: String,
    val api: String,
    val url: String
)

data class UrlScanResultResponse(
    val verdict: UrlScanVerdict,
    val task: UrlScanTask
)

data class UrlScanVerdict(
    val overall: UrlScanOverall
)

data class UrlScanOverall(
    val score: Int,
    val malicious: Boolean,
    val tags: List<String>
)

data class UrlScanTask(
    val screenshotURL: String?
)
