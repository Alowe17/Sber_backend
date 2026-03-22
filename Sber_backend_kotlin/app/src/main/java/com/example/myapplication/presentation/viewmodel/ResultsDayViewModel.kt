package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DailyResultRequest
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultsDayViewModel : ViewModel() {

    private val _todayState = MutableStateFlow<TodayState>(TodayState.Loading)
    val todayState: StateFlow<TodayState> = _todayState.asStateFlow()

    private val _submitState = MutableStateFlow<SubmitState>(SubmitState.Idle)
    val submitState: StateFlow<SubmitState> = _submitState.asStateFlow()

    fun loadTodayResults() {
        viewModelScope.launch {
            _todayState.value = TodayState.Loading
            try {
                val response = withContext(Dispatchers.IO) { UserSession.api.getTodayResults() }
                if (response.isSuccessful) {
                    response.body()?.let { map ->
                        val deals = (map["dealsCount"] as? Number)?.toInt() ?: 0
                        val volume = (map["volumeAmount"] as? Number)?.toDouble() ?: 0.0
                        val products = (map["productsCount"] as? Number)?.toInt() ?: 0
                        val isSubmitted = map["isSubmitted"] as? Boolean ?: false
                        _todayState.value = TodayState.Success(deals, volume, products, isSubmitted)
                    } ?: run {
                        _todayState.value = TodayState.Success(0, 0.0, 0, false)
                    }
                } else {
                    _todayState.value = TodayState.Error(response.message() ?: "Ошибка")
                }
            } catch (e: Exception) {
                _todayState.value = TodayState.Error(e.message ?: "Ошибка сети")
            }
        }
    }

    fun submit(dealsCount: Int, volumeAmount: Double, productsCount: Int) {
        viewModelScope.launch {
            _submitState.value = SubmitState.Loading
            try {
                val response = withContext(Dispatchers.IO) {
                    UserSession.api.saveDailyResults(
                        DailyResultRequest(dealsCount, volumeAmount, productsCount)
                    )
                }
                if (response.isSuccessful) {
                    _submitState.value = SubmitState.Success
                    loadTodayResults()
                } else {
                    _submitState.value = SubmitState.Error(response.message() ?: "Ошибка сохранения")
                }
            } catch (e: Exception) {
                _submitState.value = SubmitState.Error(e.message ?: "Ошибка сети")
            }
        }
    }

    fun resetSubmitState() {
        _submitState.value = SubmitState.Idle
    }
}

sealed class TodayState {
    data object Loading : TodayState()
    data class Success(
        val dealsCount: Int,
        val volumeAmount: Double,
        val productsCount: Int,
        val isSubmitted: Boolean
    ) : TodayState()
    data class Error(val message: String) : TodayState()
}

sealed class SubmitState {
    data object Idle : SubmitState()
    data object Loading : SubmitState()
    data object Success : SubmitState()
    data class Error(val message: String) : SubmitState()
}
