package com.jiangyy.wanandroid.ui.article

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.BaseArticlesFragment
import com.jiangyy.wanandroid.ui.main.ArticlesViewModel
import com.jiangyy.wanandroid.utils.parcelableIntent
import kotlinx.coroutines.launch

class ArticleInSubFragment private constructor() : BaseArticlesFragment() {

    private val mTree by parcelableIntent<Tree>("tree")

    override fun initObserver() {
        super.initObserver()
        val viewModel by viewModels<ArticlesViewModel>()
        lifecycleScope.launch {
            viewModel.listArticleInSub(mTree?.id.orEmpty()).collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInSubFragment()
    }


}