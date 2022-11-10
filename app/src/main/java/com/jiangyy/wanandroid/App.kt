package com.jiangyy.wanandroid

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jiangyy.core.AppContext
import com.jiangyy.core.CoreApp
import com.jiangyy.wanandroid.utils.Firewood
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import rxhttp.RxHttpPlugins
import java.util.concurrent.TimeUnit

class App : CoreApp() {

    override fun onCreate() {
        super.onCreate()
        RxHttpPlugins.init(
            OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppContext)))
                .addInterceptor(HttpLoggingInterceptor {
                    Firewood.json(it, "OkHttp Request")
                }.apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }).build()
        )
    }

}