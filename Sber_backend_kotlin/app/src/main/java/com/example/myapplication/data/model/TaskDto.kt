package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("rewardPoint") val rewardPoint: Int,
    @SerializedName("targetValue") val targetValue: Int,
    @SerializedName("eventType") val eventType: String?,
    @SerializedName("deadline") val deadline: String?
)

data class EmployeeTaskDto(
    @SerializedName("id") val id: Long,
    @SerializedName("employee") val employee: EmployeeDto?,
    @SerializedName("task") val task: TaskDto?,
    @SerializedName("progress") val progress: Int,
    @SerializedName("completed") val completed: Boolean
)
