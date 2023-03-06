package com.jiangyy.wanandroid.ui.article

import android.os.Bundle
import com.koonny.appcompat.core.argumentsString
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.ui.BaseArticleFragment

class ArticleInWechatFragment : BaseArticleFragment() {

    private val mWechatId by argumentsString("wechatId")

    override suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>> {
        return RetrofitHelper.getInstance().create(Api::class.java).pageArticleInTree(page, mWechatId.orEmpty())
    }

    companion object {
        @JvmStatic
        fun newInstance(wechatId: String) = ArticleInWechatFragment().apply {
            arguments = Bundle().apply { putString("wechatId", wechatId) }
        }
    }

}