package com.jiangyy.wanandroid.ui

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.koonny.app.BaseFragment
import com.koonny.app.core.orZero
import com.koonny.app.module.StatusModule
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.ArticleAdapter
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.FragmentArticlesBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.ui.article.ArticleActivity

/**
 * 通用文章列表
 */
abstract class BaseArticleFragment(private val startPage: Int = 0) : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate),
    TrailingLoadStateAdapter.OnTrailingListener, SwipeRefreshLayout.OnRefreshListener, StatusModule {

    private var mPage = 0
    private val mAdapter = ArticleAdapter()
    private lateinit var mHelper: QuickAdapterHelper

    protected abstract suspend fun revoke(page: Int): ApiResponse<ApiResponse.Paging<Article>>

    override fun viewBindStatus(): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mHelper = QuickAdapterHelper.Builder(mAdapter).setTrailingLoadStateAdapter(this).build()
        binding.recyclerView.adapter = mHelper.adapter
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(requireActivity(), mAdapter.getItem(position))
        }
        binding.refreshLayout.setOnRefreshListener(this)
        onRefresh()
    }

    private fun pageHomeArticle() {
        flowRequest<ApiResponse.Paging<Article>> {
            request {
                if (mPage == startPage) {
                    startLoading()
                }
                revoke(mPage)
            }
            response {
                if (it.isSuccess) {
                    if (mPage == startPage) {
                        binding.refreshLayout.isRefreshing = false
                        mAdapter.submitList(it.getOrNull()?.datas)
                        if (it.getOrNull()?.datas.isNullOrEmpty()) {
                            finishLoadingWithStatus("暂无数据", R.drawable.ic_state_empty)
                        } else {
                            finishLoading()
                        }
                    } else {
                        it.getOrNull()?.datas?.let { it1 -> mAdapter.addAll(it1) }
                    }
                    mHelper.trailingLoadState = LoadState.NotLoading(
                        it.getOrNull()?.curPage.orZero() >= it.getOrNull()?.pageCount.orZero()
                    )
                    ++mPage
                } else {
                    if (mPage == startPage) {
                        finishLoadingWithStatus("加载失败", R.drawable.ic_state_failure)
                    }
                    mHelper.trailingLoadState = LoadState.Error(it.exceptionOrNull()!!)
                }
            }
        }
    }

    override fun onRefresh() {
        mPage = startPage
        pageHomeArticle()
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
}