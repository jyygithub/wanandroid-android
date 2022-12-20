package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.*
import retrofit2.http.*

interface ApiService {

    @POST("user/logout/json")
    suspend fun logout(): Bean<Any>

    @POST("article/query/{page}/json")
    suspend fun search(@Path("page") page: Int, @Query("k") key: String): Bean<PageData<Article>>

    @GET("hotkey/json")
    suspend fun hotKey(): Bean<MutableList<HotKey>>

    @GET("article/list/{page}/json")
    suspend fun pageHomeArticle(@Path("page") page: Int): Bean<PageData<Article>>

    @GET("article/listproject/{page}/json")
    suspend fun pageHomeProject(@Path("page") page: Int): Bean<PageData<Article>>

    @GET("tree/json")
    suspend fun tree(): Bean<MutableList<Tree>>

    @GET("user/lg/userinfo/json")
    suspend fun infoUser(): Bean<UserInfo>

    @GET("message/lg/count_unread/json")
    suspend fun getUnreadMessageCount(): Bean<Int>

    @GET("coin/rank/{page}/json")
    suspend fun ranking(@Path("page") page: Int): Bean<PageData<Coin>>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Bean<User>

    @GET("lg/coin/list/{page}/json")
    suspend fun pageCoinHistory(@Path("page") page: Int): Bean<PageData<CoinHistory>>

    @GET("lg/todo/v2/list/{page}/json")
    suspend fun pageTodo(@Path("page") page: Int, @Query("status") status: Int?): Bean<PageData<Todo>>

    @GET("article/list/{page}/json")
    suspend fun pageArticleInTree(@Path("page") page: Int, @Query("cid") cid: String): Bean<PageData<Article>>

    @GET("wxarticle/chapters/json")
    suspend fun listWechat(): Bean<MutableList<Tree>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun listArticleInWechat(@Path("page") page: Int, @Path("id") id: String): Bean<PageData<Article>>

    @GET("wenda/list/{page}/json")
    suspend fun listWenda(@Path("page") page: Int): Bean<PageData<Article>>

    @GET("user_article/list/{page}/json")
    suspend fun listSquare(@Path("page") page: Int): Bean<PageData<Article>>

    @GET("article/list/{page}/json")
    suspend fun listArticleInSub(@Path("page") page: Int, @Query("cid") cid: String): Bean<PageData<Article>>

    @GET("chapter/547/sublist/json")
    suspend fun listSub(): Bean<MutableList<Tree>>

}