package com.jiangyy.wanandroid.ktor

import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Tree
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url

class ArticleApi {
    suspend fun pageHomeArticle(page: Int): PagingBean<Article> {
        return ktorClient.get {
            url("${BASE_URL}article/list/${page}/json")
        }.body()
    }

    suspend fun pageHomeProject(page: Int): Any {
        return ktorClient.get {
            url("${BASE_URL}article/listproject/${page}/json")
        }.body()
    }

    suspend fun listSquare(page: Int): Any {
        return ktorClient.get {
            url("${BASE_URL}user_article/list/${page}/json")
        }.body()
    }

    suspend fun listWenda(page: Int): Any {
        return ktorClient.get {
            url("${BASE_URL}wenda/list/${page}/json")
        }.body()
    }

    suspend fun listCollect(page: Int): Any {
        return ktorClient.get {
            url("${BASE_URL}lg/collect/list/${page}/json")
        }.body()
    }

    suspend fun listShareHistory(page: Int): Any {
        return ktorClient.get {
            url("${BASE_URL}user/lg/private_articles/${page}/json")
        }.body()
    }

    suspend fun tree(): Any {
        return ktorClient.get {
            url("${BASE_URL}tree/json")
        }.body()
    }

    suspend fun pageArticleInTree(page: Int, cid: String): Any {
        return ktorClient.get {
            url("${BASE_URL}article/list/${page}/json")
            parameter("cid", cid)
        }.body()
    }

    suspend fun pageArticleInUser(page: Int, userId: Int): Any {
        return ktorClient.get {
            url("${BASE_URL}user/${userId}/share_articles/${page}/json")
        }.body()
    }

    suspend fun listWechat(): Bean<MutableList<Tree>> {
        return ktorClient.get {
            url("${BASE_URL}wxarticle/chapters/json")
        }.body()
    }

    suspend fun listArticleInWechat(page: Int, id: String): Any {
        return ktorClient.get {
            url("${BASE_URL}wxarticle/list/${id}/${page}/json")
        }.body()
    }

    suspend fun listSub(): Any {
        return ktorClient.get {
            url("${BASE_URL}chapter/547/sublist/json")
        }.body()
    }

    suspend fun listArticleInSub(page: Int, cid: String): Any {
        return ktorClient.get {
            url("${BASE_URL}article/list/${page}/json")
            parameter("cid", cid)
        }.body()
    }

    suspend fun search(page: Int, key: String): Any {
        return ktorClient.post {
            url("${BASE_URL}article/query/${page}/json")
            parameter("k", key)
        }.body()
    }
}