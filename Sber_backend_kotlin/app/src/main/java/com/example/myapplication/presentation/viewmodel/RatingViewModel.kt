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

class RatingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RatingUiState>(RatingUiState.Loading)
    val uiState: StateFlow<RatingUiState> = _uiState.asStateFlow()

    fun loadLeaderboard() {
        viewModelScope.launch {
            _uiState.value = RatingUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) { UserSession.api.getLeaderboard() }
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        val currentUserId = UserSession.userId
                        val withCurrentFlag = list.map { emp ->
                            emp to (emp.id == currentUserId)
                        }
                        _uiState.value = RatingUiState.Success(withCurrentFlag)
                    } ?: run {
                        _uiState.value = RatingUiState.Success(emptyList())
                    }
                } else {
                    _uiState.value = RatingUiState.Error(response.message() ?: "Ошибка")
                }
            } catch (e: Exception) {
                _uiState.value = RatingUiState.Error(e.message ?: "Ошибка сети")
            }
        }
    }
}

sealed class RatingUiState {
    data object Loading : RatingUiState()
    data class Success(val leaderboard: List<Pair<EmployeeDto, Boolean>>) : RatingUiState()
    data class Error(val message: String) : RatingUiState()
}
