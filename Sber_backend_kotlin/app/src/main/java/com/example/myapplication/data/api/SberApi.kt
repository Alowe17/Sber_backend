package com.example.myapplication.data.api

import com.example.myapplication.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * API для связи с Spring Boot Backend (Sber_backend).
 * Все запросы требуют заголовок X-User-Id с ID сотрудника.
 */
interface SberApi {

    @GET("api/profile")
    suspend fun getProfile(): Response<EmployeeDto>

    @GET("api/status/{id}")
    suspend fun getStatus(@Path("id") employeeId: Long): Response<StatusDto>

    @GET("api/privileges")
    suspend fun getPrivileges(): Response<List<PrivilegeDto>>

    @GET("api/rating/details")
    suspend fun getRatingDetails(): Response<List<RatingDetailDto>>

    @GET("api/tasks")
    suspend fun getTasks(): Response<List<TaskDto>>

    @GET("api/tasks/my")
    suspend fun getMyTasks(): Response<List<EmployeeTaskDto>>

    @GET("api/leaderboard")
    suspend fun getLeaderboard(): Response<List<EmployeeDto>>

    @POST("api/daily-results")
    suspend fun saveDailyResults(@Body request: DailyResultRequest): Response<Map<String, Any>>

    @GET("api/daily-results/today")
    suspend fun getTodayResults(): Response<Map<String, Any>>

    @GET("api/daily-results/month")
    suspend fun getMonthResults(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<List<DailyResultDto>>

    @GET("api/level-config")
    suspend fun getLevelConfig(): Response<List<LevelConfigDto>>
}
