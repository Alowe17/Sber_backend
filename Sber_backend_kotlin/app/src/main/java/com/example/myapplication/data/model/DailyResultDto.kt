package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class DailyResultDto(
    @SerializedName("id") val id: Long,
    @SerializedName("dealsCount") val dealsCount: Int,
    @SerializedName("creditVolume") val creditVolume: Double,
    @SerializedName("productCount") val productCount: Int,
    @SerializedName("date") val date: String
)

data class DailyResultRequest(
    @SerializedName("dealsCount") val dealsCount: Int,
    @SerializedName("volumeAmount") val volumeAmount: Double,
    @SerializedName("productsCount") val productsCount: Int
)

data class TodayResultsResponse(
    @SerializedName("date") val date: String,
    @SerializedName("dealsCount") val dealsCount: Int,
    @SerializedName("volumeAmount") val volumeAmount: Double,
    @SerializedName("productsCount") val productsCount: Int,
    @SerializedName("isSubmitted") val isSubmitted: Boolean
)
