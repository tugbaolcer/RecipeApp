package com.tugbaolcer.recipeapp.domain.repository

import com.tugbaolcer.recipeapp.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getCategories(): Flow<List<Category>>
}