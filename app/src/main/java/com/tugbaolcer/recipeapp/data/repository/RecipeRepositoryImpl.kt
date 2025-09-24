package com.tugbaolcer.recipeapp.data.repository

import com.tugbaolcer.recipeapp.data.api.AppApi
import com.tugbaolcer.recipeapp.domain.model.Category
import com.tugbaolcer.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class RecipeRepositoryImpl(
    private val apiService: AppApi
) : RecipeRepository {

    override fun getCategories(): Flow<List<Category>> = flow {
        val response = apiService.getCategories()
        // response.categories listesi varsa onu emit et, yoksa boş liste ya da hata
        emit(response.categories)
    }.map { dtoList ->
        dtoList.map { dto ->
            Category(
                id = dto.idCategory,
                name = dto.strCategory,
                thumbnailUrl = dto.strCategoryThumb,
                description = dto.strCategoryDescription
            )
        }
    }
}
