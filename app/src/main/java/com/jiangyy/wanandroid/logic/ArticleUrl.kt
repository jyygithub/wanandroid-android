package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Article
import rxhttp.toAwait
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

class ArticleUrl {

    companion object {

        suspend fun pageHomeArticle(page: Int): Await<Beans<Article>> {
            return RxHttp.get("article/list/$page/json")
                .toAwait()
        }

    }

}