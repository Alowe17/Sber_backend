package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.StatusDto
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentStatusViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<StatusUiState>(StatusUiState.Loading)
    val uiState: StateFlow<StatusUiState> = _uiState.asStateFlow()

    fun loadStatus() {
        val userId = UserSession.userId ?: run {
            _uiState.value = StatusUiState.Error("Необходима авторизация")
            return
        }
        viewModelScope.launch {
            _uiState.value = StatusUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) { UserSession.api.getStatus(userId) }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _uiState.value = StatusUiState.Success(it)
                    } ?: run {
                        _uiState.value = StatusUiState.Error("Пустой ответ")
                    }
                } else {
                    _uiState.value = StatusUiState.Error(response.message() ?: "Ошибка ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = StatusUiState.Error(e.message ?: "Ошибка сети")
            }
        }
    }
}

sealed class StatusUiState {
    data object Loading : StatusUiState()
    data class Success(val status: StatusDto) : StatusUiState()
    data class Error(val message: String) : StatusUiState()
}
