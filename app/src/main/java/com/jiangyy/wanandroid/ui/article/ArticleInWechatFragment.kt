package com.jiangyy.wanandroid.ui.article

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.stringArgument
import com.jiangyy.wanandroid.ui.BaseArticlesFragment
import com.jiangyy.wanandroid.ui.main.ArticlesViewModel
import kotlinx.coroutines.launch

class ArticleInWechatFragment private constructor() : BaseArticlesFragment() {

    private val wechatId by stringArgument("wechatId")

    override fun initObserver() {
        val viewModel by viewModels<ArticlesViewModel>()
        lifecycleScope.launch {
            viewModel.listArticleInWechat(wechatId.orEmpty()).collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(wechatId: String) = ArticleInWechatFragment().apply {
            arguments = Bundle().apply { putString("wechatId", wechatId) }
        }
    }

}