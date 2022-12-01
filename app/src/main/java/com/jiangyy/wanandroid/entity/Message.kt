package com.jiangyy.wanandroid.entity

import android.annotation.SuppressLint
import android.net.Uri
import com.jiangyy.core.orZero
import java.text.SimpleDateFormat
import java.util.*

data class Message(
    var category: Int?,
    var date: Long?,
    var fromUser: String?,
    var fromUserId: Int?,
    var fullLink: String?,
    var id: Int?,
    var isRead: Int?,
    var link: String?,
    var message: String?,
    var niceDate: String?,
    var tag: String?,
    var title: String?,
    var userId: Int?
) {
    val realLink: String
        @SuppressLint("SimpleDateFormat")
        get() = Uri.parse(fullLink)
            .buildUpon()
            .appendQueryParameter("fid", fromUserId.toString())
            .appendQueryParameter("date", SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Date(date.orZero())))
            .appendQueryParameter("message", message)
            .appendQueryParameter("scrollToKeywords", message.orEmpty()
                .substring(0, message.orEmpty().length.coerceAtMost(20))
                .split(Regex("[ ]"))
                .joinToString(","))
            .build()
            .toString()
}