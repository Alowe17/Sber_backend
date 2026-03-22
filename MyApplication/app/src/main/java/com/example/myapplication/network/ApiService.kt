package com.example.myapplication.network

import com.example.myapplication.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/status/{id}")
    suspend fun getStatus(@Path("id") employeeId: Long): StatusDto

    @GET("api/privileges")
    suspend fun getPrivileges(@Query("employeeId") employeeId: Long): List<PrivilegeDto>

    @GET("api/rating/details")
    suspend fun getRatingDetails(@Query("employeeId") employeeId: Long): List<RatingDetailDto>

    @POST("api/daily-results")
    suspend fun sendDailyResult(
        @Query("employeeId") employeeId: Long,
        @Body request: DailyResultRequest
    )
}
