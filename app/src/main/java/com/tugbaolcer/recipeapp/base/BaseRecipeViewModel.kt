package com.tugbaolcer.recipeapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class UiState<out T> {
    data class Loading(val showProgress: Boolean = true) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

open class BaseRecipeViewModel : ViewModel() {

    protected val _uiState = MutableStateFlow<UiState<Any>>(UiState.Loading(showProgress = false))
    val uiState: StateFlow<UiState<Any>> get() = _uiState

    protected fun <T> execute(
        showLoading: Boolean = false,
        block: suspend () -> T
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading(showProgress = showLoading)
                val result = block()
                _uiState.value = UiState.Success(result as Any)
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    400 -> "Geçersiz istek"
                    401 -> "Yetkisiz erişim"
                    404 -> "Veri bulunamadı"
                    500 -> "Sunucu hatası"
                    else -> "Bilinmeyen hata: ${e.code()}"
                }
                _uiState.value = UiState.Error(errorMessage)
            }
            catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
