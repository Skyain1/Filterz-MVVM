package com.example.filterz.Utilities

import android.app.Application
import com.example.filterz.dependencyinjection.repositoryNodule
import com.example.filterz.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


@Suppress("unused")
class AppConfig:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryNodule, viewModelModule))
        }
    }
}