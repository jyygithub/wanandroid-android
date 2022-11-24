package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Coin
import com.jiangyy.wanandroid.entity.CoinHistory
import com.jiangyy.wanandroid.entity.User
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

        suspend fun infoCoin(): Await<Bean<Coin>> {
            return RxHttp.get("lg/coin/userinfo/json")
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

    }

}