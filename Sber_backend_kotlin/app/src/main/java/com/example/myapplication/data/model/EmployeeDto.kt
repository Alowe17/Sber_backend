package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class EmployeeDto(
    @SerializedName("id") val id: Long,
    @SerializedName("fullName") val fullName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("currentLevel") val currentLevel: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("dealerCenter") val dealerCenter: DealerCenterDto?,
    @SerializedName("registrationDate") val registrationDate: String?,
    @SerializedName("ratingPoints") val ratingPoints: Int = 0,
    @SerializedName("sberId") val sberId: Int = 0
)

data class DealerCenterDto(
    @SerializedName("id") val id: Long,
    @SerializedName("code") val code: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("region") val region: RegionDto?
)

data class RegionDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("code") val code: String?
)
