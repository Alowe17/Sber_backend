package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.EmployeeTaskDto
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksMonthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<TasksMonthUiState>(TasksMonthUiState.Loading)
    val uiState: StateFlow<TasksMonthUiState> = _uiState.asStateFlow()

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = TasksMonthUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) { UserSession.api.getMyTasks() }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _uiState.value = TasksMonthUiState.Success(it)
                    } ?: run {
                        _uiState.value = TasksMonthUiState.Success(emptyList())
                    }
                } else {
                    _uiState.value = TasksMonthUiState.Error(response.message() ?: "Ошибка")
                }
            } catch (e: Exception) {
                _uiState.value = TasksMonthUiState.Error(e.message ?: "Ошибка сети")
            }
        }
    }
}

sealed class TasksMonthUiState {
    data object Loading : TasksMonthUiState()
    data class Success(val tasks: List<EmployeeTaskDto>) : TasksMonthUiState()
    data class Error(val message: String) : TasksMonthUiState()
}
