package com.tugbaolcer.recipeapp.data.api

import com.tugbaolcer.recipeapp.data.dto.GetCategoriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface AppApi {
    @GET("categories.php")
    suspend fun getCategories(): Response<GetCategoriesResponse>
}