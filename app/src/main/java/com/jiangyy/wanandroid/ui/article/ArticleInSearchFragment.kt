package com.jiangyy.wanandroid.ui.article

import androidx.fragment.app.activityViewModels
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.ui.BaseArticleFragment

class ArticleInSearchFragment : BaseArticleFragment() {

    private val mViewModel by activityViewModels<SearchViewModel>()

    private var mKey = ""

    override fun onPrepareData() {
        if (mKey.isBlank()) {
            finishLoadingWithStatus("暂无数据", R.drawable.ic_state_empty)
        }
        mViewModel.key.observe(this) {
            if (it.isNullOrBlank()) {
                finishLoadingWithStatus("暂无数据", R.drawable.ic_state_empty)
            } else {
                mKey = it
                super.onPrepareData()
            }
        }
    }

    override suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>> {
        return RetrofitHelper.getInstance().create(Api::class.java).search(page, mKey)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInSearchFragment()
    }

}