package com.tugbaolcer.recipeapp.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tugbaolcer.recipeapp.BuildConfig
import com.tugbaolcer.recipeapp.data.api.AppApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {

        return HttpLoggingInterceptor().apply {

            this.level = if (BuildConfig.DEBUG) {

                HttpLoggingInterceptor.Level.BODY

            } else {

                HttpLoggingInterceptor.Level.NONE

            }

        }

    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) /** data classı desteği */
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        loggingInterceptor: HttpLoggingInterceptor,
        moshi: Moshi
    ): AppApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT)
            .client(
                OkHttpClient.Builder()
                    .readTimeout(40, TimeUnit.SECONDS)
                    .connectTimeout(40, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(AppApi::class.java)
    }
}