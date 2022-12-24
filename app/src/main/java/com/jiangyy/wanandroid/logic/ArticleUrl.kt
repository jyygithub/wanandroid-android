package com.jiangyy.wanandroid.logic

import rxhttp.toAwait
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

class ArticleUrl {

    companion object {

        suspend fun unshare(id: String): Await<Bean<Any>> {
            return RxHttp.postForm("lg/user_article/delete/$id/json")
                .toAwait()
        }

    }

}