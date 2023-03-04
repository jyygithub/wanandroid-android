package com.jiangyy.wanandroid.ui.article

import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.ui.BaseArticleFragment

/**
 * 分享记录
 */
class ArticleInShareFragment : BaseArticleFragment() {

    override val startPage: Int get() = 1

    override suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>> {
        return RetrofitHelper.getInstance().create(Api::class.java).listShareHistory(page)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInShareFragment()
    }

}