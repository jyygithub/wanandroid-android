package com.jiangyy.wanandroid.ui.user

import android.view.View
import androidx.fragment.app.activityViewModels
import com.jiangyy.common.view.BaseLoadFragment
import com.jiangyy.wanandroid.ui.article.ArticlesViewModel
import com.jiangyy.wanandroid.databinding.ContentArticlesBinding
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import com.jiangyy.wanandroid.utils.DataStoreUtils

class ScanHistoryFragment : BaseLoadFragment<ContentArticlesBinding>(ContentArticlesBinding::inflate) {

    override val viewBindStatus: View get() = binding.refreshLayout

    private val mAdapter = ArticleAdapter()

    private val mArticlesViewModel by activityViewModels<ArticlesViewModel>()

    override fun initWidget() {
        super.initWidget()
        binding.recyclerView.adapter = mAdapter
        mAdapter.itemClick { position ->
            ArticleActivity.actionStart(requireActivity(), mAdapter.getItem(position))
        }
        binding.refreshLayout.setOnRefreshListener {
            preLoad()
        }
        mArticlesViewModel.scans().observe(this) {
            mAdapter.submitList = null
        }
    }

    override fun preLoad() {
        super.preLoad()
        preLoadSuccess()
        binding.refreshLayout.isRefreshing = false
        mAdapter.submitList = DataStoreUtils.getScanHistory()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ScanHistoryFragment()
    }

}