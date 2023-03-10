package com.jiangyy.wanandroid.data

import com.jiangyy.wanandroid.entity.*
import retrofit2.http.*

interface Api {

    /**
     * 获取最新版本
     */
    @GET("https://api.github.com/repos/jyygithub/wanandroid/releases/latest")
    suspend fun latestVersion(): AppVersion

    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): ApiResponse<User>

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    suspend fun logout(): ApiResponse<Any>

    /**
     * 获取用户信息
     */
    @GET("user/lg/userinfo/json")
    suspend fun infoUser(): ApiResponse<UserInfo>

    /**
     * 获取积分排行榜
     */
    @GET("coin/rank/{page}/json")
    suspend fun ranking(@Path("page") page: Int): ApiResponse<ApiResponse.Paging<Coin>>

    /**
     * 获取积分记录
     */
    @GET("lg/coin/list/{page}/json")
    suspend fun pageCoinHistory(@Path("page") page: Int): ApiResponse<ApiResponse.Paging<CoinHistory>>

    /**
     * 首页文章
     */
    @GET("article/list/{page}/json")
    suspend fun pageHomeArticle(@Path("page") page: Int): ApiResponse<ApiResponse.Paging<Article>>

    /**
     * 首页项目
     */
    @GET("article/listproject/{page}/json")
    suspend fun pageHomeProject(@Path("page") page: Int): ApiResponse<ApiResponse.Paging<Article>>

    /**
     * 广场列表
     */
    @GET("user_article/list/{page}/json")
    suspend fun listSquare(@Path("page") page: Int): ApiResponse<ApiResponse.Paging<Article>>

    /**
     * 问答列表
     */
    @GET("wenda/list/{page}/json")
    suspend fun listWenda(@Path("page") page: Int): ApiResponse<ApiResponse.Paging<Article>>

    /**
     * 获取收藏记录
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun listCollect(@Path("page") page: Int): ApiResponse<ApiResponse.Paging<Article>>

    /**
     * 获取分享记录
     */
    @GET("user/lg/private_articles/{page}/json")
    suspend fun listShareHistory(@Path("page") page: Int): ApiResponse<ShareHistory>

    /**
     * 体系列表
     */
    @GET("tree/json")
    suspend fun tree(): ApiResponse<MutableList<Tree>>

    /**
     * 体系下的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun pageArticleInTree(
        @Path("page") page: Int,
        @Query("cid") cid: String
    ): ApiResponse<ApiResponse.Paging<Article>>

    @GET("user/{userId}/share_articles/{page}/json")
    suspend fun pageArticleInUser(@Path("page") page: Int, @Path("userId") userId: Int): ApiResponse<ShareHistory>

    /**
     * 公众号列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun listWechat(): ApiResponse<MutableList<Tree>>

    /**
     * 公众号文章列表
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun listArticleInWechat(
        @Path("page") page: Int,
        @Path("id") id: String
    ): ApiResponse<ApiResponse.Paging<Article>>

    /**
     * 获取教程列表
     */
    @GET("chapter/547/sublist/json")
    suspend fun listSub(): ApiResponse<MutableList<Tree>>

    /**
     * 教程下的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun listArticleInSub(
        @Path("page") page: Int,
        @Query("cid") cid: String
    ): ApiResponse<ApiResponse.Paging<Article>>

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun search(@Path("page") page: Int = 0, @Field("k") k: String): ApiResponse<ApiResponse.Paging<Article>>

}