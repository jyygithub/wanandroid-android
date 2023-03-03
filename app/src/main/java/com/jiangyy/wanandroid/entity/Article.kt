package com.jiangyy.wanandroid.entity

import android.os.Parcelable
import android.text.Spanned
import androidx.core.text.HtmlCompat
import kotlinx.parcelize.Parcelize

val String?.htmlString: Spanned get() = HtmlCompat.fromHtml(this.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)

@Parcelize
class Article(
    val title: String,
    val author: String,
    val niceDate: String,
    val chapterName: String,
    val superChapterName: String,
    val link: String?,
    val envelopePic: String?,
    val shareUser: String?,
) : Parcelable