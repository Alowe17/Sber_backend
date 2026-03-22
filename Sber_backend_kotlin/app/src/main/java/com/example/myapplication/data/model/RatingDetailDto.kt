package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class RatingDetailDto(
    @SerializedName("type") val type: String,
    @SerializedName("points") val points: Long
)
