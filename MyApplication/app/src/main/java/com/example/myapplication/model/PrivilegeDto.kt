package com.example.myapplication.model

data class PrivilegeDto(
    val name: String,
    val description: String,
    val effectMoney: Int,
    val active: Boolean,
    val unlockLevel: String
)