package com.jiangyy.wanandroid.ktor

import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Tree
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class ArticleApi {
    suspend fun articles(path: String, page: Int, param: Pair<String, String>? = null): PagingBean<Article> {
        return ktorClient.get {
            url("${BASE_URL}${path}/${page}/json")
            if (param != null) {
                parameter(param.first, param.second)
            }
        }.body()
    }

    suspend fun listWechat(): Bean<MutableList<Tree>> {
        return ktorClient.get {
            url("${BASE_URL}wxarticle/chapters/json")
        }.body()
    }

    suspend fun listSub(): Bean<MutableList<Tree>> {
        return ktorClient.get {
            url("${BASE_URL}chapter/547/sublist/json")
        }.body()
    }

}