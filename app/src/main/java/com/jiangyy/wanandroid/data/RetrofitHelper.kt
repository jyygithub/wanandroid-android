package com.jiangyy.wanandroid.data

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jiangyy.wanandroid.AppContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private const val BASE_URL = "https://www.wanandroid.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppContext)))
                    .addInterceptor(
                        HttpLoggingInterceptor {
                            try {
                                Timber.v(JSONObject(it).toString(4))
                            } catch (e: Exception) {
                                Timber.v(it)
                            }
                        }.setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}