package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.PrivilegeDto
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonalFinancialEffectViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<FinancialEffectUiState>(FinancialEffectUiState.Loading)
    val uiState: StateFlow<FinancialEffectUiState> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = FinancialEffectUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) { UserSession.api.getPrivileges() }
                if (response.isSuccessful) {
                    response.body()?.let { privileges ->
                        val totalBenefit = privileges.filter { it.isActive }.sumOf { it.effectMoney }
                        _uiState.value = FinancialEffectUiState.Success(
                            totalBenefit = totalBenefit,
                            privileges = privileges
                        )
                    } ?: run {
                        _uiState.value = FinancialEffectUiState.Success(0, emptyList())
                    }
                } else {
                    _uiState.value = FinancialEffectUiState.Error(response.message() ?: "Ошибка")
                }
            } catch (e: Exception) {
                _uiState.value = FinancialEffectUiState.Error(e.message ?: "Ошибка сети")
            }
        }
    }
}

sealed class FinancialEffectUiState {
    data object Loading : FinancialEffectUiState()
    data class Success(val totalBenefit: Int, val privileges: List<PrivilegeDto>) : FinancialEffectUiState()
    data class Error(val message: String) : FinancialEffectUiState()
}
