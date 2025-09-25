package com.tugbaolcer.recipeapp.di.module


import com.tugbaolcer.recipeapp.data.repository.RecipeRepositoryImpl
import com.tugbaolcer.recipeapp.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository
}