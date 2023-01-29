package com.jiangyy.wanandroid

import android.app.Application
import android.content.ContextWrapper

private lateinit var INSTANCE: Application

object AppContext : ContextWrapper(INSTANCE)

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}