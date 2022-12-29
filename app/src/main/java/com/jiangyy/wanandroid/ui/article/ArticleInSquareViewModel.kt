package com.jiangyy.wanandroid.ui.article

import com.jiangyy.wanandroid.data.PageViewModel
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData

class ArticleInSquareViewModel : PageViewModel<Article>() {

    override suspend fun realRequest(page: Int): Bean<PageData<Article>> {
        return API_SERVICE.listSquare(page)
    }

}