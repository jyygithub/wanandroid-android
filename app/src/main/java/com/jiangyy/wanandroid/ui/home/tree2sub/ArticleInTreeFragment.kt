package com.jiangyy.wanandroid.ui.home.tree2sub

import com.koonny.appcompat.core.intentParcelable
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.BaseArticleFragment

class ArticleInTreeFragment : BaseArticleFragment() {

    private val mTree by intentParcelable<Tree>("tree")

    override suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>> {
        return RetrofitHelper.getInstance().create(Api::class.java).pageArticleInTree(page, mTree?.id.orEmpty())
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInTreeFragment()
    }

}