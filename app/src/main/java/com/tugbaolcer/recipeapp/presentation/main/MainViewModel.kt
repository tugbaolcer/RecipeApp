package com.tugbaolcer.recipeapp.presentation.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugbaolcer.recipeapp.domain.model.Category
import com.tugbaolcer.recipeapp.domain.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>?>(emptyList())
    val categories: StateFlow<List<Category>?> get() = _categories

    var categoryName = MutableLiveData<String>()

    fun fetchCategories() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .catch { e ->
                    Log.d("LOG_ERROR_MESSAGE", "message: ${e.message}")
                }
                .collect { result ->
                    _categories.value = result
                    Log.d("LOG_DATA", "data: ${result.first().name}")
                    categoryName.value = result.first().name
                }
        }
    }
}