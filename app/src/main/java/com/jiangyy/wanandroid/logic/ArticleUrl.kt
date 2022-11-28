package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Coin
import com.jiangyy.wanandroid.entity.HotKey
import com.jiangyy.wanandroid.entity.Tree
import rxhttp.toAwait
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

class ArticleUrl {

    companion object {

        suspend fun pageHomeArticle(page: Int): Await<Beans<Article>> {
            return RxHttp.get("article/list/$page/json")
                .toAwait()
        }

        suspend fun pageHomeProject(page: Int): Await<Beans<Article>> {
            return RxHttp.get("article/listproject/$page/json")
                .toAwait()
        }

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

        suspend fun pageArticleInTree(page: Int, cid: String): Await<Beans<Article>> {
            return RxHttp.get("article/list/$page/json")
                .addQuery("cid", cid)
                .toAwait()
        }

        suspend fun listWechat(): Await<Bean<MutableList<Tree>>> {
            return RxHttp.get("wxarticle/chapters/json")
                .toAwait()
        }

        suspend fun listArticleInWechat(page: Int, id: String): Await<Beans<Article>> {
            return RxHttp.get("wxarticle/list/$id/$page/json")
                .toAwait()
        }

        suspend fun listWenda(page: Int): Await<Beans<Article>> {
            return RxHttp.get("wenda/list/$page/json")
                .toAwait()
        }

        suspend fun listSquare(page: Int): Await<Beans<Article>> {
            return RxHttp.get("user_article/list/$page/json")
                .toAwait()
        }

        suspend fun listSub(): Await<Bean<MutableList<Tree>>> {
            return RxHttp.get("chapter/547/sublist/json")
                .toAwait()
        }

        suspend fun listArticleInSub(page: Int, id: String): Await<Beans<Article>> {
            return RxHttp.get("article/list/$page/json")
                .addQuery("cid", id)
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

    }

}