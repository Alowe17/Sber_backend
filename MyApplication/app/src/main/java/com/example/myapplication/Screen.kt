package com.example.myapplication

sealed class Screen(val route: String) {
    object Status : Screen("status")
    object Details : Screen("details")
    object Calculator : Screen("calculator")
}