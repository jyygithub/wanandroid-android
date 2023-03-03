package com.jiangyy.wanandroid

import android.app.Application
import android.content.ContextWrapper
import timber.log.Timber

private lateinit var INSTANCE: Application

object AppContext : ContextWrapper(INSTANCE)

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}