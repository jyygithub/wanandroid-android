package com.jiangyy.wanandroid.entity

import com.jiangyy.wanandroid.data.ApiResponse

class ShareHistory(
    val shareArticles: ApiResponse.Paging<Article>?
)