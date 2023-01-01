package com.jiangyy.wanandroid.logic

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jiangyy.core.AppContext
import com.jiangyy.wanandroid.utils.Firewood
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://www.wanandroid.com/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppContext)))
            .addInterceptor(HttpLoggingInterceptor {
                Firewood.json(it, "Firewood")
            }.apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()
    )
    .build()

val API_SERVICE = retrofit.create(ApiService::class.java)