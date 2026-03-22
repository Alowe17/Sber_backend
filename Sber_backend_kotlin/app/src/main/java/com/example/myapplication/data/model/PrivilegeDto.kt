package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class PrivilegeDto(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("effectMoney") val effectMoney: Int,
    @SerializedName("active") val isActive: Boolean,
    @SerializedName("unlockedLevel") val unlockedLevel: String?
)
