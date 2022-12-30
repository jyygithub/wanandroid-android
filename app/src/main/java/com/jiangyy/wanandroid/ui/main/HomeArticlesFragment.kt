package com.jiangyy.wanandroid.ui.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.ui.BaseArticlesFragment
import kotlinx.coroutines.launch

class HomeArticlesFragment private constructor() : BaseArticlesFragment() {

    override fun initObserver() {
        val viewModel by viewModels<ArticlesViewModel>()
        lifecycleScope.launch {
            viewModel.pageHomeArticles().collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeArticlesFragment()
    }

}