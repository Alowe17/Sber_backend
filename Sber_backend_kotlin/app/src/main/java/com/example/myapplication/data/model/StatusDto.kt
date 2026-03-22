package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class StatusDto(
    @SerializedName("currentLevel") val currentLevel: String,
    @SerializedName("currentPoints") val currentPoints: Int,
    @SerializedName("pointsToNext") val pointsToNext: Int,
    @SerializedName("progressPercent") val progressPercent: Int,
    @SerializedName("nextLevel") val nextLevel: String?
)
