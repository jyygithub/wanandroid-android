package com.jiangyy.wanandroid.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jiangyy.wanandroid.AppContext
import com.jiangyy.wanandroid.entity.Article
import kotlinx.coroutines.flow.map

data class CurrentUser(
    var username: String? = null,
    var nickname: String? = null,
)

val Context.dataStore by preferencesDataStore(AppContext.packageName)

fun Context.localUser() =
    dataStore.data.map {
        Gson().fromJson(
            it[stringPreferencesKey("currentUser")] ?: Gson().toJson(CurrentUser()),
            CurrentUser::class.java
        )
    }

fun Context.localScan() =
    dataStore.data.map { preference ->
        val result = preference[stringPreferencesKey("scan")] ?: Gson().toJson(mutableListOf<Article>())
        Gson().fromJson<MutableList<Article>>(result, object : TypeToken<MutableList<Article>>() {}.type)
    }

suspend fun Context.localScan(article: Article) {
    dataStore.edit { preference ->
        val result = preference[stringPreferencesKey("scan")] ?: Gson().toJson(mutableListOf<Article>())
        val list = Gson().fromJson<MutableList<Article>>(result, object : TypeToken<MutableList<Article>>() {}.type)
        list.add(0, article)
        preference[stringPreferencesKey("scan")] = Gson().toJson(list)
    }
}

suspend fun Context.localLogout() {
    dataStore.edit { preference ->
        preference[stringPreferencesKey("currentUser")] = Gson().toJson(CurrentUser())
        preference[stringPreferencesKey("scan")] = Gson().toJson(mutableListOf<Article>())
    }
}

suspend fun Context.localLogin(currentUser: CurrentUser) {
    dataStore.edit {
        it[stringPreferencesKey("currentUser")] = Gson().toJson(currentUser)
    }
}