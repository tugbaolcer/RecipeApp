package com.tugbaolcer.recipeapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

open class BaseRecipeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Any>>(UiState.Loading)
    val uiState: StateFlow<UiState<Any>> get() = _uiState

    protected fun <T> execute(
        block: suspend () -> T
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val result = block()
                _uiState.value = UiState.Success(result as Any)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
