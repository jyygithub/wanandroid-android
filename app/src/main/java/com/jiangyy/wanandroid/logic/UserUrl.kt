package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Coin
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

    }

}