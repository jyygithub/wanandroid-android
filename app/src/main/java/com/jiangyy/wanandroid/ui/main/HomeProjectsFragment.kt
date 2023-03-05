package com.jiangyy.wanandroid.ui.main

import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.ui.BaseArticleFragment

class HomeProjectsFragment : BaseArticleFragment(0) {

    override suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>> {
        return RetrofitHelper.getInstance().create(Api::class.java).pageHomeProject(page)
    }

    companion object {
        fun newInstance() = HomeProjectsFragment()
    }
}