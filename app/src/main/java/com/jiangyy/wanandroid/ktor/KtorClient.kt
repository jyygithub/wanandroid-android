package com.jiangyy.wanandroid.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.gson.gson
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.text.DateFormat

private const val TIMEOUT_IN_MILLIS = 30_000 //30 seconds

const val BASE_URL = "https://www.wanandroid.com/"

val ktorClient = HttpClient(Android) {

    engine {
        connectTimeout = TIMEOUT_IN_MILLIS
        socketTimeout = TIMEOUT_IN_MILLIS
    }

    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                try {
                    if (message.startsWith("{")) {
                        Timber.tag("timber-http").v(JSONObject(message).toString(4))
                    } else if (message.startsWith("[")) {
                        Timber.tag("timber-http").v(JSONArray(message).toString(4))
                    } else {
                        Timber.tag("timber-http").v(message)
                    }
                } catch (e: Exception) {
                    Timber.tag("timber-http").e(message)
                }
            }
        }
        level = LogLevel.ALL
    }

    install(DefaultRequest) {
    }
}