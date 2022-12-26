package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Article
import rxhttp.toAwait
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

class UserUrl {

    companion object {

        suspend fun listCollect(page: Int): Await<Beans<Article>> {
            return RxHttp.get("lg/collect/list/$page/json")
                .toAwait()
        }

    }

}