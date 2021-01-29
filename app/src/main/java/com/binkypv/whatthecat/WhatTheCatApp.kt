package com.binkypv.whatthecat

import android.app.Application
import com.binkypv.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WhatTheCatApp: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WhatTheCatApp)
            modules(
                Modules.viewModelModule,
                Modules.repositoryModule,
                Modules.dataSourceModule,
                Modules.netModule
            )
        }
    }
}