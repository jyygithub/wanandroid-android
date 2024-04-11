package com.jiangyy.wanandroid.ktor

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url

class CoinApi {
    suspend fun ranking(page : Int): Any {
        return ktorClient.post {
            url("${BASE_URL}coin/rank/${page}/json")
        }.body()
    }
    suspend fun pageCoinHistory(page : Int): Any {
        return ktorClient.post {
            url("${BASE_URL}lg/coin/list/${page}/json")
        }.body()
    }
}