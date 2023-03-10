package com.jiangyy.wanandroid.ui

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.ArticleAdapter
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.flowPagingRequest
import com.jiangyy.wanandroid.databinding.FragmentArticlesBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import com.koonny.appcompat.BaseFragment
import com.koonny.appcompat.module.StatusModule

/**
 * 通用文章列表
 */
abstract class BaseArticleFragment(private val startPage: Int = 0) :
    BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate),
    TrailingLoadStateAdapter.OnTrailingListener, SwipeRefreshLayout.OnRefreshListener, StatusModule {

    private var mPage = 0
    private val mAdapter = ArticleAdapter()
    private lateinit var mHelper: QuickAdapterHelper

    protected abstract suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>>

    override fun viewBindStatus(): View {
        return binding.root
    }

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        mHelper = QuickAdapterHelper.Builder(mAdapter).setTrailingLoadStateAdapter(this).build()
        binding.recyclerView.adapter = mHelper.adapter
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(requireActivity(), mAdapter.getItem(position))
        }
        binding.refreshLayout.setOnRefreshListener(this)
    }

    override fun onPrepareData() {
        super.onPrepareData()
        mPage = startPage
        pageHomeArticle()
    }

    override fun onRefresh() {
        onPrepareData()
    }

    override fun onStatusRetry() {
        onRefresh()
    }

    override fun onFailRetry() {
        pageHomeArticle()
    }

    override fun onLoad() {
        pageHomeArticle()
    }

    override fun isAllowLoading(): Boolean {
        return !binding.refreshLayout.isRefreshing
    }

    private fun pageHomeArticle() {
        flowPagingRequest(mPage, startPage) {
            request {
                if (it == startPage) {
                    startLoading()
                }
                revoke(it)
            }
            onEmpty { finishLoadingWithStatus("暂无数据", R.drawable.ic_state_empty) }
            onError { finishLoadingWithStatus(it.message.orEmpty(), R.drawable.ic_state_failure) }
            onFinish { finishLoading() }
            onLoadError {
                mHelper.trailingLoadState = LoadState.Error(it)
            }
            onLoadFinish { data, isComplete ->
                mAdapter.addAll(data)
                mHelper.trailingLoadState = LoadState.NotLoading(isComplete)
            }
            onCurrentPage { mPage = it }
        }
    }
}