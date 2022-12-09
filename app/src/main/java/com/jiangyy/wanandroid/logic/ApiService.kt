package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Coin
import com.jiangyy.wanandroid.entity.User
import com.jiangyy.wanandroid.entity.UserInfo
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("user/logout/json")
    suspend fun logout(): Bean<Any>

    @GET("article/list/{page}/json")
    suspend fun pageHomeArticle(@Path("page") page: Int): Bean<PageData<Article>>

    @GET("article/listproject/{page}/json")
    suspend fun pageHomeProject(@Path("page") page: Int): Bean<PageData<Article>>

    @GET("user/lg/userinfo/json")
    suspend fun infoUser(): Bean<UserInfo>

    @GET("message/lg/count_unread/json")
    suspend fun getUnreadMessageCount(): Bean<Int>

    @GET("coin/rank/{page}/json")
    suspend fun ranking(@Path("page") page: Int): Bean<PageData<Coin>>

    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Bean<User>

}