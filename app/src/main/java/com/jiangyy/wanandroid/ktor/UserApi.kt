package com.jiangyy.wanandroid.ktor

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url

class UserApi {
    suspend fun login(username: String, password: String): Any {
        return ktorClient.post {
            url("${BASE_URL}user/login")
            parameter("username", username)
            parameter("password", password)
        }.body()
    }

    suspend fun logout(): Any {
        return ktorClient.get {
            url("${BASE_URL}user/logout/json")
        }.body()
    }

    suspend fun info(): Any {
        return ktorClient.get {
            url("${BASE_URL}user/lg/userinfo/json")
        }.body()
    }
}