package com.jiangyy.wanandroid.ui.article

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.parcelableIntent
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.BaseArticlesFragment
import com.jiangyy.wanandroid.ui.main.ArticlesViewModel
import kotlinx.coroutines.launch

class ArticleInWechatFragment private constructor() : BaseArticlesFragment() {

    private val mTree by parcelableIntent<Tree>("tree")

    override fun initObserver() {
        val viewModel by viewModels<ArticlesViewModel>()
        lifecycleScope.launch {
            viewModel.listArticleInWechat(mTree?.id.orEmpty()).collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInWechatFragment()
    }

}