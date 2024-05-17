package com.jiangyy.wanandroid.ktor

import com.jiangyy.wanandroid.entity.Ranking
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class CoinApi {
    suspend fun ranking(page: Int): PagingBean<Ranking> {
        return ktorClient.get {
            url("${BASE_URL}coin/rank/${page}/json")
        }.body()
    }

    suspend fun pageCoinHistory(page: Int): Any {
        return ktorClient.get {
            url("${BASE_URL}lg/coin/list/{page}/json")
        }.body()
    }
}