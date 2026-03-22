package com.example.myapplication.session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.data.api.ApiClient
import com.example.myapplication.data.api.SberApi

/**
 * Глобальная сессия пользователя.
 * Хранит ID сотрудника после входа и предоставляет экземпляр API.
 */
object UserSession {

    var userId: Long? by mutableStateOf(null)
        private set

    val isLoggedIn: Boolean
        get() = userId != null

    private var _api: SberApi? = null

    val api: SberApi
        get() {
            val id = userId ?: throw IllegalStateException("User not logged in")
            return _api ?: ApiClient.create(id).also { _api = it }
        }

    fun login(employeeId: Long) {
        userId = employeeId
        _api = ApiClient.create(employeeId)
    }

    fun logout() {
        userId = null
        _api = null
    }
}
