package com.tugbaolcer.recipeapp

import android.app.Application
import androidx.appcompat.app.AlertDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.tugbaolcer.recipeapp.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.*
import javax.inject.Inject


class App : Application(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
    }
}