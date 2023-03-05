package com.jiangyy.wanandroid.ui.article

import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.ui.BaseArticleFragment

/**
 * 广场
 */
class ArticleInSquareFragment : BaseArticleFragment() {

    override suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>> {
        return RetrofitHelper.getInstance().create(Api::class.java).listSquare(page)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInSquareFragment()
    }

}