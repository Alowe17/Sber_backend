package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.EmployeeDto
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) { UserSession.api.getProfile() }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _uiState.value = ProfileUiState.Success(it)
                    } ?: run {
                        _uiState.value = ProfileUiState.Error("Пустой ответ")
                    }
                } else {
                    _uiState.value = ProfileUiState.Error(response.message() ?: "Ошибка ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Ошибка сети")
            }
        }
    }
}

sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data class Success(val profile: EmployeeDto) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
