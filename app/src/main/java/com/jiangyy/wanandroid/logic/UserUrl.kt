package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Message
import rxhttp.toAwait
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

class UserUrl {

    companion object {

        suspend fun listReadedMessage(page: Int): Await<Beans<Message>> {
            return RxHttp.get("message/lg/readed_list/$page/json")
                .toAwait()
        }

        suspend fun listUnreadMessage(page: Int): Await<Beans<Message>> {
            return RxHttp.get("message/lg/unread_list/$page/json")
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