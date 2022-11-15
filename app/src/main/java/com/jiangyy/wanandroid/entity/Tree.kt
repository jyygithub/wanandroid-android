package com.jiangyy.wanandroid.entity

import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tree(
    val children: MutableList<Tree>?,
    val courseId: String?,
    val id: String?,
    val name: String?,
    val cover: String?,
    val order: Int?,
    val parentChapterId: Int?,
) : MultiItemEntity, Parcelable {

    override val itemType: Int
        get() = if ((parentChapterId ?: 0) < 1) 0 else 1

    @IgnoredOnParcel
    var isChecked: Boolean = false

}