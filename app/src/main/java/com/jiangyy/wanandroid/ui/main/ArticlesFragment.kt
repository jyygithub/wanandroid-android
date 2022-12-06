package com.jiangyy.wanandroid.ui.main

import androidx.fragment.app.viewModels
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.FragmentArticlesBinding
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity

class ArticlesFragment : BaseLoadFragment<FragmentArticlesBinding>(), MultipleStateModule {

    private val mAdapter = ArticleAdapter()

    private val mViewModel by viewModels<ArticlesViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.toolbar.setTitle("最新文章")
        binding.toolbar.setOnEndListener {
            SearchActivity.actionStart(requireActivity())
        }
        binding.recyclerView.adapter = mAdapter
        binding.refreshLayout.setOnRefreshListener {
            mViewModel.firstLoad()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMore()
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            ArticleActivity.actionStart(requireActivity(), item)
        }

        mViewModel.firstData().observe(this) {
            if (it.datas.isEmpty()) {
                preLoadWithEmpty("暂无数据")
            } else {
                preLoadSuccess()
                mAdapter.addData(it.datas)
                if (mAdapter.data.size == it.total) {
                    mAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    mAdapter.loadMoreModule.loadMoreComplete()
                }
            }
        }
        mViewModel.loadMoreData().observe(this) {
            if (it.datas.isEmpty()) {
                mAdapter.loadMoreModule.loadMoreEnd()
            } else {
                mAdapter.addData(it.datas)
                if (mAdapter.data.size == it.total) {
                    mAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    mAdapter.loadMoreModule.loadMoreComplete()
                }
            }
        }
        mViewModel.dataError().observe(this) {
            if (it.second) {
                mAdapter.loadMoreModule.loadMoreFail()
            } else {
                preLoadWithFailure(it.first.message.orEmpty()) {
                    preLoad()
                }
            }
        }

    }

    override fun preLoad() {
        mViewModel.firstLoad()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticlesFragment()
    }

}