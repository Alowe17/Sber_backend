package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.StatusDto
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScenarioCalculatorViewModel : ViewModel() {

    private val _statusState = MutableStateFlow<ScenarioStatusState>(ScenarioStatusState.Loading)
    val statusState: StateFlow<ScenarioStatusState> = _statusState.asStateFlow()

    fun loadStatus() {
        val userId = UserSession.userId ?: run {
            _statusState.value = ScenarioStatusState.Error("Необходима авторизация")
            return
        }
        viewModelScope.launch {
            _statusState.value = ScenarioStatusState.Loading
            try {
                val response = withContext(Dispatchers.IO) { UserSession.api.getStatus(userId) }
                if (response.isSuccessful) {
                    response.body()?.let {
                        _statusState.value = ScenarioStatusState.Success(it)
                    } ?: run {
                        _statusState.value = ScenarioStatusState.Error("Пустой ответ")
                    }
                } else {
                    _statusState.value = ScenarioStatusState.Error(response.message() ?: "Ошибка")
                }
            } catch (e: Exception) {
                _statusState.value = ScenarioStatusState.Error(e.message ?: "Ошибка сети")
            }
        }
    }
}

sealed class ScenarioStatusState {
    data object Loading : ScenarioStatusState()
    data class Success(val status: StatusDto) : ScenarioStatusState()
    data class Error(val message: String) : ScenarioStatusState()
}
