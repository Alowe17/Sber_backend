package com.example.myapplication.model

@Serializable
data class RatingDetailDto(
    val type: String,
    val points: Long
)