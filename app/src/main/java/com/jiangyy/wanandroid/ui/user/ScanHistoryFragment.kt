package com.jiangyy.wanandroid.ui.user

import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.ContentArticlesBinding
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import com.jiangyy.wanandroid.utils.DataStoreUtils

class ScanHistoryFragment : BaseLoadFragment<ContentArticlesBinding>(), MultipleStateModule {

    private val mAdapter = ArticleAdapter()

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