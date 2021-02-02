package com.binkypv.whatthecat

import android.app.Application
import com.binkypv.whatthecat.utils.InstantAppExecutors
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TestApp)
            modules(listOf(
                module {
                    single { InstantAppExecutors() }
                }
            ))
        }
    }
}