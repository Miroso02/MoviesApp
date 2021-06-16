package com.example.movies

import android.app.Application

class MyApplication : Application() {
    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(applicationContext))
            .build()
    }
}