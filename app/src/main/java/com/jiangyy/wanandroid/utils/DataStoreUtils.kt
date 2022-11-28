package com.jiangyy.wanandroid.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jiangyy.core.AppContext
import com.jiangyy.wanandroid.data.RefreshScan
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.greenrobot.eventbus.EventBus

object DataStoreUtils {

    var logged
        get() = getValue("logged", false)
        set(value) = putValue("logged", value)

    var currentUser: CurrentUser
        private set(value) = putValue("currentUser", Gson().toJson(value))
        get() {
            val userJson = getValue("currentUser", Gson().toJson(CurrentUser()))
            return Gson().fromJson(userJson, CurrentUser::class.java)
        }

    data class CurrentUser(
        var username: String? = null,
        var nickname: String? = null,
    )

    fun updateUser(user: User?) {
        currentUser = CurrentUser(
            user?.username,
            user?.nickname,
        )
    }

    fun logout() {
        logged = false
        updateUser(null)
        clearScan()
        clearSearch()
    }

    fun search(key: String) {
        getSearchHistory().also {
            it.add(0, key)
            putValue("search", Gson().toJson(it))
        }
    }

    fun getSearchHistory(): MutableList<String> {
        val result = getValue("search", Gson().toJson(mutableListOf<String>()))
        if (result.isBlank()) return mutableListOf()
        val list = Gson().fromJson<MutableList<String>>(result, object : TypeToken<MutableList<String>>() {}.type)
        if (list == null || list.size < 6) return list
        return list.subList(0, 5)
    }

    fun clearSearch() {
        putValue("search", Gson().toJson(mutableListOf<String>()))
    }

    fun scan(article: Article?) {
        if (article == null) return
        getScanHistory().also {
            it.add(0, article)
            putValue("scan", Gson().toJson(it))
            EventBus.getDefault().post(RefreshScan())
        }
    }

    fun getScanHistory(): MutableList<Article> {
        val result = getValue("scan", Gson().toJson(mutableListOf<Article>()))
        return if (result.isBlank()) mutableListOf()
        else Gson().fromJson(result, object : TypeToken<MutableList<Article>>() {}.type)
    }

    fun clearScan() {
        putValue("scan", Gson().toJson(mutableListOf<Article>()))
        EventBus.getDefault().post(RefreshScan())
    }

    // -----------

    private val Context.dataStore0: DataStore<Preferences> by preferencesDataStore(name = AppContext.packageName)

    private val dataStore = AppContext.dataStore0

    private fun putValue(key: String, value: Int) {
        val _key = intPreferencesKey(key)
        runBlocking {
            dataStore.edit { settings ->
                settings[_key] = value
            }
        }
    }

    private fun getValue(key: String, default: Int): Int {
        val _key = intPreferencesKey(key)
        return runBlocking {
            dataStore.data.map { settings ->
                settings[_key] ?: default
            }.first()
        }
    }

    private fun putValue(key: String, value: Long) {
        val _key = longPreferencesKey(key)
        runBlocking {
            dataStore.edit { settings ->
                settings[_key] = value
            }
        }
    }

    private fun getValue(key: String, default: Long): Long {
        val _key = longPreferencesKey(key)
        return runBlocking {
            dataStore.data.map { settings ->
                settings[_key] ?: default
            }.first()
        }
    }

    private fun putValue(key: String, value: String) {
        val _key = stringPreferencesKey(key)
        runBlocking {
            dataStore.edit { settings ->
                settings[_key] = value
            }
        }
    }

    private fun getValue(key: String, default: String): String {
        val _key = stringPreferencesKey(key)
        return runBlocking {
            dataStore.data.map { settings ->
                settings[_key] ?: default
            }.first()
        }
    }

    private fun putValue(key: String, value: Boolean) {
        val _key = booleanPreferencesKey(key)
        runBlocking {
            dataStore.edit { settings ->
                settings[_key] = value
            }
        }
    }

    private fun getValue(key: String, default: Boolean): Boolean {
        val _key = booleanPreferencesKey(key)
        return runBlocking {
            dataStore.data.map { settings ->
                settings[_key] ?: default
            }.first()
        }
    }

    private fun putValue(key: String, value: Float) {
        val _key = floatPreferencesKey(key)
        runBlocking {
            dataStore.edit { settings ->
                settings[_key] = value
            }
        }
    }

    private fun getValue(key: String, default: Float): Float {
        val _key = floatPreferencesKey(key)
        return runBlocking {
            dataStore.data.map { settings ->
                settings[_key] ?: default
            }.first()
        }
    }

    private fun putValue(key: String, value: Double) {
        val _key = doublePreferencesKey(key)
        runBlocking {
            dataStore.edit { settings ->
                settings[_key] = value
            }
        }
    }

    private fun getValue(key: String, default: Double): Double {
        val _key = doublePreferencesKey(key)
        return runBlocking {
            dataStore.data.map { settings ->
                settings[_key] ?: default
            }.first()
        }
    }

}