package com.jiangyy.wanandroid.ui.article

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.ui.BaseArticlesFragment
import com.jiangyy.wanandroid.ui.main.ArticlesViewModel
import kotlinx.coroutines.launch

class ArticleInWendaFragment private constructor() : BaseArticlesFragment() {

    override fun initObserver() {
        super.initObserver()
        val viewModel by viewModels<ArticlesViewModel>()
        lifecycleScope.launch {
            viewModel.listWenda().collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInWendaFragment()
    }

}