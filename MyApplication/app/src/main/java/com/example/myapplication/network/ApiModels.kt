package com.example.myapplication.network

data class StatusResponse(
    val currentLevel: String? = null,
    val currentPoints: Int = 0,
    val progressPercent: Int = 0,
    val pointsToNext: Int = 0,
    val nextLevel: String? = null
)

data class RatingDetailResponse(
    val type: String,
    val points: Long
)

data class DailyResultRequest(
    val dealsCount: Int,
    val volumeAmount: Double,
    val productsCount: Int
)
