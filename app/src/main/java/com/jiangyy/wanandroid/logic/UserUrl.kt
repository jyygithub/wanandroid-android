package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.*
import rxhttp.toAwait
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

class UserUrl {

    companion object {

        suspend fun ranking(page: Int): Await<Beans<Coin>> {
            return RxHttp.get("coin/rank/$page/json")
                .toAwait()
        }

        suspend fun login(username: String, password: String): Await<Bean<User>> {
            return RxHttp.postForm("user/login")
                .add("username", username)
                .add("password", password)
                .toAwait()
        }

        suspend fun infoUser(): Await<Bean<UserInfo>> {
            return RxHttp.get("user/lg/userinfo/json")
                .toAwait()
        }

        suspend fun listCoinHistory(page: Int): Await<Beans<CoinHistory>> {
            return RxHttp.get("lg/coin/list/$page/json")
                .toAwait()
        }

        suspend fun listShareHistory(page: Int): Await<Beans<Article>> {
            return RxHttp.get("user/lg/private_articles/$page/json")
                .toAwait()
        }

        suspend fun listCollect(page: Int): Await<Beans<Article>> {
            return RxHttp.get("lg/collect/list/$page/json")
                .toAwait()
        }

    }

}