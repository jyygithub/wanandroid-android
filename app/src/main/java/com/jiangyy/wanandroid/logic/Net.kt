package com.jiangyy.wanandroid.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jiangyy.core.AppContext
import com.jiangyy.wanandroid.utils.Firewood
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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

class Action<T> {
    //发起请求
    var request: (suspend () -> Bean<T>)? = null
        private set

    //请求成功
    var success: ((T?) -> Unit)? = null
        private set

    //请求失败
    var error: ((Throwable) -> Unit)? = null
        private set

    fun request(block: suspend () -> Bean<T>) {
        request = block
    }

    fun success(block: (T?) -> Unit) {
        success = block
    }

    fun error(block: (Throwable) -> Unit) {
        error = block
    }
}

fun <T> ViewModel.netRequest(block: Action<T>.() -> Unit) {
    val action = Action<T>().apply(block)
    viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
        action.error?.invoke(throwable)
    }) {
        val result = withContext(Dispatchers.IO) {
            action.request!!.invoke()
        }
        if (result.errorCode == 0) {
            action.success?.invoke(result.data)
        } else {
            action.error?.invoke(Exception(result.errorMsg.orEmpty()))
        }
    }
}