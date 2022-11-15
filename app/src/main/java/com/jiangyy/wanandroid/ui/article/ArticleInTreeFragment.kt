package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.parcelableIntent
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.ContentArticlesBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.logic.ArticleUrl
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import kotlinx.coroutines.launch
import rxhttp.awaitResult

class ArticleInTreeFragment : BaseLoadFragment<ContentArticlesBinding>(), MultipleStateModule {

    private val mAdapter = ArticleAdapter()
    private var mPage = 0
    private val mTree by parcelableIntent<Tree>("tree")

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(requireActivity(), mAdapter.getItem(position))
        }
        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            loadMore()
        }
    }

    override fun preLoad() {
        refresh()
    }

    private fun refresh() {
        mPage = 0
        mAdapter.setList(null)
        lifecycleScope.launch {
            ArticleUrl.pageArticleInTree(mPage, mTree?.id.orEmpty())
                .awaitResult {
                    binding.refreshLayout.isRefreshing = false
                    if (it.isSuccess()) {
                        if (it.data?.datas.isNullOrEmpty()) {
                            preLoadWithEmpty("暂无数据")
                        } else {
                            preLoadSuccess()
                            mAdapter.addData(it.data?.datas!!)
                            if (mAdapter.data.size == it.data.total) {
                                mAdapter.loadMoreModule.loadMoreEnd()
                            } else {
                                mAdapter.loadMoreModule.loadMoreComplete()
                                ++mPage
                            }
                        }
                    } else {
                        preLoadWithFailure(it.errorMsg.orEmpty()) {
                            preLoad()
                        }
                    }
                }
                .onFailure {
                    binding.refreshLayout.isRefreshing = false
                    preLoadWithFailure {
                        preLoad()
                    }
                }
        }
    }

    private fun loadMore() {
        lifecycleScope.launch {
            ArticleUrl.pageArticleInTree(mPage, mTree?.id.orEmpty())
                .awaitResult {
                    if (it.isSuccess()) {
                        if (it.data?.datas.isNullOrEmpty()) {
                            mAdapter.loadMoreModule.loadMoreEnd()
                        } else {
                            mAdapter.addData(it.data?.datas!!)
                            if (mAdapter.data.size == it.data.total) {
                                mAdapter.loadMoreModule.loadMoreEnd()
                            } else {
                                mAdapter.loadMoreModule.loadMoreComplete()
                                ++mPage
                            }
                        }
                    } else {
                        mAdapter.loadMoreModule.loadMoreFail()
                    }
                }
                .onFailure {
                    mAdapter.loadMoreModule.loadMoreFail()
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInTreeFragment()
    }

}