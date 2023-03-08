package com.jiangyy.wanandroid.ui.article

import android.os.Bundle
import com.koonny.appcompat.core.intentParcelable
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.BaseArticleFragment
import com.koonny.appcompat.core.argumentsString

class ArticleInSearchFragment : BaseArticleFragment() {

    private val mKey by argumentsString("k")

    override suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>> {
        return RetrofitHelper.getInstance().create(Api::class.java).search(page, mKey.orEmpty())
    }

    companion object {
        @JvmStatic
        fun newInstance(key: String) = ArticleInSearchFragment().apply {
            arguments = Bundle().apply {
                putString("k", key)
            }
        }
    }

}