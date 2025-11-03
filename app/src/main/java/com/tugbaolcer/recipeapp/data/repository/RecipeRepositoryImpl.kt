package com.tugbaolcer.recipeapp.data.repository

import com.tugbaolcer.recipeapp.data.remote.api.AppApi
import com.tugbaolcer.recipeapp.domain.model.Category
import com.tugbaolcer.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val apiService: AppApi
) : RecipeRepository {

    override fun getCategories(): Flow<List<Category>> = flow {
        val response = apiService.getCategories()
        if (response.isSuccessful) {
            response.body()?.categories?.let { dtoList ->
                emit(dtoList.map { dto ->
                    Category(
                        id = dto.idCategory,
                        name = dto.strCategory,
                        thumbnailUrl = dto.strCategoryThumb,
                        description = dto.strCategoryDescription
                    )
                })
            } ?: emit(emptyList()) // body null ise boş liste
        } else {
            throw Exception("API error: ${response.code()} ${response.message()}")
        }
    }
}

