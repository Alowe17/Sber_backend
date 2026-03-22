package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.RatingDetailDto
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RatingDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RatingDetailsUiState>(RatingDetailsUiState.Loading)
    val uiState: StateFlow<RatingDetailsUiState> = _uiState.asStateFlow()

    fun loadRatingDetails() {
        viewModelScope.launch {
            _uiState.value = RatingDetailsUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) { UserSession.api.getRatingDetails() }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _uiState.value = RatingDetailsUiState.Success(it)
                    } ?: run {
                        _uiState.value = RatingDetailsUiState.Error("Пустой ответ")
                    }
                } else {
                    _uiState.value = RatingDetailsUiState.Error(response.message() ?: "Ошибка")
                }
            } catch (e: Exception) {
                _uiState.value = RatingDetailsUiState.Error(e.message ?: "Ошибка сети")
            }
        }
    }
}

sealed class RatingDetailsUiState {
    data object Loading : RatingDetailsUiState()
    data class Success(val details: List<RatingDetailDto>) : RatingDetailsUiState()
    data class Error(val message: String) : RatingDetailsUiState()
}
