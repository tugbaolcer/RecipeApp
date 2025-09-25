package com.tugbaolcer.recipeapp.data.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDto(
    @field:Json(name = "idCategory") val idCategory: String,
    @field:Json(name = "strCategory") val strCategory: String,
    @field:Json(name = "strCategoryThumb") val strCategoryThumb: String,
    @field:Json(name = "strCategoryDescription") val strCategoryDescription: String
) : Parcelable

@Parcelize
data class GetCategoriesResponse(
    @field:Json(name = "categories") val categories: List<CategoryDto>
) : Parcelable