package com.jiangyy.wanandroid.ui.user

import androidx.fragment.app.activityViewModels
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.ui.article.ArticlesViewModel
import com.jiangyy.wanandroid.databinding.ContentArticlesBinding
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import com.jiangyy.wanandroid.utils.DataStoreUtils

class ScanHistoryFragment : BaseLoadFragment<ContentArticlesBinding>(), MultipleStateModule {

    private val mAdapter = ArticleAdapter()

    private val mArticlesViewModel by activityViewModels<ArticlesViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(requireActivity(), mAdapter.getItem(position))
        }
        binding.refreshLayout.setOnRefreshListener {
            preLoad()
        }
        mArticlesViewModel.scans().observe(this) {
            mAdapter.setList(null)
        }
    }

    override fun preLoad() {

        preLoadSuccess()

        mAdapter.setList(DataStoreUtils.getScanHistory())

    }

    companion object {
        @JvmStatic
        fun newInstance() = ScanHistoryFragment()
    }

}