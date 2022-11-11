package com.jiangyy.wanandroid.logic

import rxhttp.wrapper.annotation.DefaultDomain

@JvmField
@DefaultDomain
public var BASE_URL = "https://www.wanandroid.com/"

class Bean<T>(
    val errorCode: Int?,
    val errorMsg: String?,
    val data: T?
) {
    fun isSuccess(): Boolean = (this.errorCode == SUCCESS)

    companion object {
        const val SUCCESS = 0
    }
}

class Beans<T>(
    val errorCode: Int?,
    val errorMsg: String?,
    val data: PageData<T>?
) {
    fun isSuccess(): Boolean = (this.errorCode == SUCCESS)

    companion object {
        const val SUCCESS = 0
    }
}

class PageData<T>(
    val curPage: Int?,
    val offset: Int?,
    val datas: MutableList<T>,
    val over: Boolean?,
    val pageCount: Int?,
    val size: Int?,
    val total: Int?
)
