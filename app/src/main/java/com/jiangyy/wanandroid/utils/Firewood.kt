package com.jiangyy.wanandroid.utils

import android.text.Spanned
import android.util.Log
import androidx.core.text.HtmlCompat
import com.google.gson.Gson
import com.jiangyy.wanandroid.BuildConfig
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

val String.htmlString: Spanned
    get() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)

class Firewood private constructor() {

    companion object {
        private fun log(priority: Int, tag: String?, message: String?) {
            if (!BuildConfig.DEBUG) return
            val t = tag ?: "Default TAG"
            if (message.isNullOrBlank()) {
                Log.println(priority, t, "Firewood NULL")
            } else {
                Log.println(priority, t, message)
            }
        }

        fun bean(data: Any?, tag: String? = null) {
            if (data == null) d("Object is NULL")
            else json(Gson().toJson(data), tag)
        }

        fun d(message: String?, tag: String? = null) {
            log(Log.DEBUG, tag, message)
        }

        fun v(message: String?, tag: String? = null) {
            log(Log.VERBOSE, tag, message)
        }

        fun json(data: String?, tag: String? = null) {
            val result: String? = try {
                when {
                    data.isNullOrBlank() -> data
                    data.startsWith("[") -> JSONArray(data).toString(4)
                    data.startsWith("{") -> JSONObject(data).toString(4)
                    else -> data
                }
            } catch (e: JSONException) {
                data
            }
//            log(Log.VERBOSE, tag, "╒==========================================================================")
            log(Log.VERBOSE, tag, result)
//            log(Log.VERBOSE, tag, "╘==========================================================================")
        }
    }

}