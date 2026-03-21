package com.example.myapplication.model

@Serializable
data class DailyResultRequest(
    val employeeId: Long,
    val deals: Int,
    val volume: Double,
    val products: Int
)