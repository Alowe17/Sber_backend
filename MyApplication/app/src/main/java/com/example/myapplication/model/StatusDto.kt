package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class StatusDto(
    val currentLevel: String,
    val currentPoints: Int,
    val pointsToNext: Int,
    val progressPercent: Int,
    val nextLevel: String
)