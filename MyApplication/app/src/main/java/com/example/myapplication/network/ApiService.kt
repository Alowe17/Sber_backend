package com.example.myapplication.network

import com.example.myapplication.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET("api/status/current")
    suspend fun getStatus(@Header("X-User-Id") employeeId: Long): StatusDto

    @GET("api/privileges")
    suspend fun getPrivileges(@Header("X-User-Id") employeeId: Long): List<PrivilegeDto>

    @GET("api/rating/details")
    suspend fun getRatingDetails(@Header("X-User-Id") employeeId: Long): List<RatingDetailDto>

    @POST("api/daily-results")
    suspend fun sendDailyResult(
        @Header("X-User-Id") employeeId: Long,
        @Body request: DailyResultRequest
    )
}