package com.tugbaolcer.recipeapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugbaolcer.recipeapp.domain.model.Category
import com.tugbaolcer.recipeapp.domain.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchCategories(handleOnSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            getCategoriesUseCase()
                .catch { e ->
                    _error.value = e.message
                }
                .collect { result ->
                    _categories.value = result
                }
            _isLoading.value = false
        }
    }
}