package com.jiangyy.wanandroid.ui.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.ui.BaseArticlesFragment
import kotlinx.coroutines.launch

class ProjectsFragment private constructor() : BaseArticlesFragment() {

    override fun initObserver() {
        super.initObserver()
        val viewModel by viewModels<ArticlesViewModel>()
        lifecycleScope.launch {
            viewModel.pageHomeProject().collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectsFragment()
    }


}