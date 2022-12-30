package com.jiangyy.wanandroid.logic

class Bean<T>(
    val errorCode: Int?,
    val errorMsg: String?,
    val data: T?,
)

class PageData<T>(
    val curPage: Int?,
    val offset: Int?,
    val datas: MutableList<T>,
    val over: Boolean?,
    val pageCount: Int?,
    val size: Int?,
    val total: Int?,
)