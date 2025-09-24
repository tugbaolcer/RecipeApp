package com.tugbaolcer.recipeapp.di.component

import android.app.Application
import com.tugbaolcer.recipeapp.App
import com.tugbaolcer.recipeapp.di.module.AppModule
import com.tugbaolcer.recipeapp.di.module.NetworkModule
import com.tugbaolcer.recipeapp.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        RepositoryModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}