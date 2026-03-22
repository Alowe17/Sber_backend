package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class LevelConfigDto(
    @SerializedName("id") val id: Long,
    @SerializedName("currentLevel") val currentLevel: String,
    @SerializedName("minPoints") val minPoints: Int
)
