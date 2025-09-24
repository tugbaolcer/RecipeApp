package com.tugbaolcer.recipeapp.data.dto

data class CategoryDto(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)

data class GetCategoriesResponse(
    val categories: List<CategoryDto>
)