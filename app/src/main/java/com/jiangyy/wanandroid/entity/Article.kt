package com.jiangyy.wanandroid.entity

import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val author: String?,
    val shareUser: String?,
    val niceDate: String?,
    val desc: String?,
    val title: String?,
    val envelopePic: String?,
    val chapterName: String?,
    val superChapterName: String?,
    val link: String?,
    val id: String?,
    val originId: String?,
    var collect: Boolean?,
) : MultiItemEntity, Parcelable {
    override val itemType: Int get() = if (envelopePic.isNullOrEmpty()) 0 else 1
}