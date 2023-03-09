package com.jiangyy.wanandroid.ui.home.sub

import android.os.Bundle
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.BaseArticleFragment
import com.koonny.appcompat.core.argumentsParcelable

/**
 * 教程
 */
class ArticleInSubFragment : BaseArticleFragment() {

    private val mTree by argumentsParcelable<Tree>("tree")

    override suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>> {
        return RetrofitHelper.getInstance().create(Api::class.java).listArticleInSub(page, mTree?.id.orEmpty())
    }

    companion object {
        @JvmStatic
        fun newInstance(tree: Tree) = ArticleInSubFragment().apply {
            arguments = Bundle().apply {
                putParcelable("tree", tree)
            }
        }
    }

}