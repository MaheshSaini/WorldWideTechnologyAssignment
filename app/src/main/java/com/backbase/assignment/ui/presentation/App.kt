package com.backbase.assignment.ui.presentation

import android.app.Application
import com.backbase.assignment.ui.presentation.di.networkModule
import com.backbase.assignment.ui.presentation.di.repositoryModule
import com.backbase.assignment.ui.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        /*add koin di*/
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}
