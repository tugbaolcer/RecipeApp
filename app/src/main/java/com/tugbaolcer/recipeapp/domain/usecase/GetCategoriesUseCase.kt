package com.tugbaolcer.recipeapp.domain.usecase

import com.tugbaolcer.recipeapp.domain.model.Category
import com.tugbaolcer.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(
    private val repository: RecipeRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return repository.getCategories()
    }
}