package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.HotKey
import com.jiangyy.wanandroid.entity.Tree
import rxhttp.toAwait
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

class ArticleUrl {

    companion object {

        suspend fun search(page: Int, key: String): Await<Beans<Article>> {
            return RxHttp.postForm("article/query/$page/json")
                .add("k", key)
                .toAwait()
        }

        suspend fun hotKey(): Await<Bean<MutableList<HotKey>>> {
            return RxHttp.get("hotkey/json")
                .toAwait()
        }

        suspend fun tree(): Await<Bean<MutableList<Tree>>> {
            return RxHttp.get("tree/json")
                .toAwait()
        }

        /**
         * 收藏站外文章
         */
        suspend fun collect(title: String, author: String, link: String): Await<Bean<Any>> {
            return RxHttp.postForm("lg/collect/add/json")
                .add("title", title)
                .add("author", author)
                .add("link", link)
                .toAwait()
        }

        /**
         * 收藏站内文章
         */
        suspend fun collect(id: String): Await<Bean<Any>> {
            return RxHttp.postForm("lg/collect/$id/json")
                .toAwait()
        }

        /**
         * 取消收藏
         */
        suspend fun uncollect(id: String): Await<Bean<Any>> {
            return RxHttp.postForm("lg/uncollect_originId/$id/json")
                .toAwait()
        }

        /**
         * 取消收藏
         */
        suspend fun uncollect(id: String, originId: String): Await<Bean<Any>> {
            return RxHttp.postForm("lg/uncollect/$id/json")
                .add("originId", originId)
                .toAwait()
        }

        suspend fun share(title: String, link: String): Await<Bean<Any>> {
            return RxHttp.postForm("lg/user_article/add/json")
                .add("title", title)
                .add("link", link)
                .toAwait()
        }

        suspend fun unshare(id: String): Await<Bean<Any>> {
            return RxHttp.postForm("lg/user_article/delete/$id/json")
                .toAwait()
        }

    }

}