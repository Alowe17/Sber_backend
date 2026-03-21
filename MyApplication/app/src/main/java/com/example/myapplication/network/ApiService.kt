package com.example.myapplication.network

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.example.myapplication.model.*

object ApiService {

    suspend fun getStatus(employeeId: Long): StatusDto {
        return ApiClient.client.get("${ApiClient.BASE_URL}/status/$employeeId")
            .body()
    }

    suspend fun getPrivileges(employeeId: Long): List<PrivilegeDto> {
        return ApiClient.client.get("${ApiClient.BASE_URL}/privileges/$employeeId")
            .body()
    }

    suspend fun getRatingDetails(employeeId: Long): List<RatingDetailDto> {
        return ApiClient.client.get("${ApiClient.BASE_URL}/rating/$employeeId")
            .body()
    }

    suspend fun sendDailyResult(request: DailyResultRequest) {
        ApiClient.client.post("${ApiClient.BASE_URL}/daily-results") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}