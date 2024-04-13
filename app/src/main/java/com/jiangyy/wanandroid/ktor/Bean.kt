package com.jiangyy.wanandroid.ktor

data class Bean<T>(
    var errorMsg: String?,
    var errorCode: Int,
    var data: T,
) {
    class Paging<R>(
        var curPage: Int,
        var pageCount: Int,
        var size: Int,
        var total: Int,
        var datas: List<R>,
    )
}