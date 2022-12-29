package com.jiangyy.wanandroid.ui.article

import androidx.fragment.app.viewModels
import com.jiangyy.core.parcelableIntent
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.ContentArticlesBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.logic.loadData
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter

class ArticleInSubFragment : BaseLoadFragment<ContentArticlesBinding>(), MultipleStateModule {

    private val mAdapter = ArticleAdapter()

    private val mTree by parcelableIntent<Tree>("tree")

    private val mViewModel by viewModels<ArticleInSubViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(requireActivity(), mAdapter.getItem(position))
        }
        binding.refreshLayout.setOnRefreshListener {
            mViewModel.firstLoad()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMore()
        }
        mViewModel.pageData.observe(this) {
            this.loadData(it, mAdapter, binding.refreshLayout, mViewModel)
        }
    }

    override fun preLoad() {
        mViewModel.fetchParam(mTree?.id.orEmpty())
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInSubFragment()
    }


}