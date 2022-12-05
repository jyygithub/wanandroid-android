package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Article
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("article/list/{page}/json")
    suspend fun pageHomeArticle(@Path("page") page: Int): Bean<PageData<Article>>

}