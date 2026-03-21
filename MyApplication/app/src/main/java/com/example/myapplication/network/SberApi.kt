package com.example.myapplication.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SberApi {
    @GET("api/status/current")
    suspend fun getCurrentStatus(@Header("X-User-Id") userId: Long): StatusResponse

    @GET("api/rating/details")
    suspend fun getRatingDetails(@Header("X-User-Id") userId: Long): List<RatingDetailResponse>

    @POST("api/daily-results")
    suspend fun saveDailyResult(
        @Header("X-User-Id") userId: Long,
        @Body request: DailyResultRequest
    ): Map<String, Any>
}
