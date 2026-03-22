package com.example.myapplication.model

data class StatusDto(
    val currentLevel: String,
    val currentPoints: Int,
    val pointsToNext: Int,
    val progressPercent: Int,
    val nextLevel: String
)