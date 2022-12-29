package com.jiangyy.wanandroid.ui.article

import com.jiangyy.wanandroid.data.PageViewModel
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData

class ArticleIntTreeViewModel : PageViewModel<Article>() {

    private var mCid = ""

    override suspend fun realRequest(page: Int): Bean<PageData<Article>> {
        return  API_SERVICE.pageArticleInTree(page, mCid)
    }

    fun fetchParam(cid: String) {
        mCid = cid
        firstLoad()
    }

}