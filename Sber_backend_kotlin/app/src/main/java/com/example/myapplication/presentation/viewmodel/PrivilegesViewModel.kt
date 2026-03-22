package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.PrivilegeDto
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrivilegesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PrivilegesUiState>(PrivilegesUiState.Loading)
    val uiState: StateFlow<PrivilegesUiState> = _uiState.asStateFlow()

    fun loadPrivileges() {
        viewModelScope.launch {
            _uiState.value = PrivilegesUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) { UserSession.api.getPrivileges() }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _uiState.value = PrivilegesUiState.Success(it)
                    } ?: run {
                        _uiState.value = PrivilegesUiState.Error("Пустой ответ")
                    }
                } else {
                    _uiState.value = PrivilegesUiState.Error(response.message() ?: "Ошибка ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = PrivilegesUiState.Error(e.message ?: "Ошибка сети")
            }
        }
    }
}

sealed class PrivilegesUiState {
    data object Loading : PrivilegesUiState()
    data class Success(val privileges: List<PrivilegeDto>) : PrivilegesUiState()
    data class Error(val message: String) : PrivilegesUiState()
}
