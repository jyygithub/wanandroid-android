package com.jiangyy.wanandroid.ktor

import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Tree
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class ArticleApi {
    suspend fun articles(path: String, page: Int): PagingBean<Article> {
        return ktorClient.get {
            url("${BASE_URL}${path}/${page}/json")
        }.body()
    }
    suspend fun listArticleInWechat(page: Int, id: String): Any {
        return ktorClient.get {
            url("${BASE_URL}wxarticle/list/${id}/${page}/json")
        }.body()
    }

    suspend fun pageHomeArticle(page: Int): PagingBean<Article> {
        return ktorClient.get {
            url("${BASE_URL}article/list/${page}/json")
        }.body()
    }

    suspend fun pageHomeProject(page: Int): PagingBean<Article> {
        return ktorClient.get {
            url("${BASE_URL}article/listproject/${page}/json")
        }.body()
    }

    suspend fun listWechat(): Bean<MutableList<Tree>> {
        return ktorClient.get {
            url("${BASE_URL}wxarticle/chapters/json")
        }.body()
    }
}