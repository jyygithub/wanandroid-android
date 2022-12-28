package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.*
import retrofit2.http.*

interface ApiService {

    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Bean<User>

    /**
     * 退出登录
     */
    @POST("user/logout/json")
    suspend fun logout(): Bean<Any>

    /**
     * 获取用户信息
     */
    @GET("user/lg/userinfo/json")
    suspend fun infoUser(): Bean<UserInfo>

    /**
     * 获取未读消息数
     */
    @GET("message/lg/count_unread/json")
    suspend fun getUnreadMessageCount(): Bean<Int>

    /**
     * 获取未读消息列表
     */
    @GET("message/lg/unread_list/{page}/json")
    suspend fun listUnreadMessage(@Path("page") page: Int): Bean<PageData<Message>>

    /**
     * 获取已读消息列表
     */
    @GET("message/lg/readed_list/{page}/json")
    suspend fun listReadedMessage(@Path("page") page: Int): Bean<PageData<Message>>

    /**
     * 获取积分排行榜
     */
    @GET("coin/rank/{page}/json")
    suspend fun ranking(@Path("page") page: Int): Bean<PageData<Coin>>

    /**
     * 获取积分记录
     */
    @GET("lg/coin/list/{page}/json")
    suspend fun pageCoinHistory(@Path("page") page: Int): Bean<PageData<CoinHistory>>

    /**
     * 文章搜索
     */
    @POST("article/query/{page}/json")
    suspend fun search(@Path("page") page: Int, @Query("k") key: String): Bean<PageData<Article>>

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    suspend fun hotKey(): Bean<MutableList<HotKey>>

    /**
     * 首页文章
     */
    @GET("article/list/{page}/json")
    suspend fun pageHomeArticle(@Path("page") page: Int): Bean<PageData<Article>>

    /**
     * 首页项目
     */
    @GET("article/listproject/{page}/json")
    suspend fun pageHomeProject(@Path("page") page: Int): Bean<PageData<Article>>

    /**
     * 体系列表
     */
    @GET("tree/json")
    suspend fun tree(): Bean<MutableList<Tree>>

    /**
     * 体系下的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun pageArticleInTree(@Path("page") page: Int, @Query("cid") cid: String): Bean<PageData<Article>>

    /**
     * 公众号列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun listWechat(): Bean<MutableList<Tree>>

    /**
     * 公众号文章列表
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun listArticleInWechat(@Path("page") page: Int, @Path("id") id: String): Bean<PageData<Article>>

    /**
     * 问答列表
     */
    @GET("wenda/list/{page}/json")
    suspend fun listWenda(@Path("page") page: Int): Bean<PageData<Article>>

    /**
     * 广场列表
     */
    @GET("user_article/list/{page}/json")
    suspend fun listSquare(@Path("page") page: Int): Bean<PageData<Article>>

    /**
     * 教程下的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun listArticleInSub(@Path("page") page: Int, @Query("cid") cid: String): Bean<PageData<Article>>

    /**
     * 获取教程列表
     */
    @GET("chapter/547/sublist/json")
    suspend fun listSub(): Bean<MutableList<Tree>>

    /**
     * 获取分享记录
     */
    @GET("user/lg/private_articles/{page}/json")
    suspend fun listShareHistory(@Path("page") page: Int): Bean<PageData<Article>>

    /**
     * 获取收藏记录
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun listCollect(@Path("page") page: Int): Bean<PageData<Article>>

    /**
     * 分享文章
     */
    @FormUrlEncoded
    @POST("lg/user_article/add/json")
    suspend fun share(@Field("title") title: String, @Field("link") link: String): Bean<Any>

    /**
     * 取消分享文章
     */
    @POST("lg/user_article/delete/{id}/json")
    suspend fun unshare(@Path("id") id: String): Bean<Any>

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: String): Bean<Any>

    /**
     * 取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: String): Bean<Any>

    /**
     * 获取待办列表
     */
    @GET("lg/todo/v2/list/{page}/json")
    suspend fun pageTodo(@Path("page") page: Int, @Query("status") status: Int?): Bean<PageData<Todo>>

    /**
     * 新增待办
     */
    @FormUrlEncoded
    @POST("lg/todo/add/json")
    suspend fun addTodo(@Field("title") title: String, @Field("content") content: String, @Field("date") date: String): Bean<Any>

}