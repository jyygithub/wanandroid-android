package com.jiangyy.wanandroid.ui.main

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.jiangyy.app.BaseFragment
import com.jiangyy.app.module.StatusModule
import com.jiangyy.wanandroid.adapter.ArticleAdapter
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.FragmentArticlesBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.ui.article.ArticleActivity

class HomeProjectsFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate),
    TrailingLoadStateAdapter.OnTrailingListener, OnRefreshListener, StatusModule {

    private var mPage = 0
    private val mAdapter = ArticleAdapter()
    private lateinit var mHelper: QuickAdapterHelper

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
                if (mPage == 0) {
                    startLoading()
                }
                RetrofitHelper.getInstance().create(Api::class.java).pageHomeProject(mPage)
            }
            response {
                if (it.isSuccess) {
                    if (it.getOrNull()?.curPage == 1) {
                        finishLoading()
                        binding.refreshLayout.isRefreshing = false
                        mAdapter.submitList(it.getOrNull()?.datas)
                    } else {
                        mAdapter.addAll(it.getOrNull()?.datas!!)
                    }
                    mHelper.trailingLoadState = LoadState.NotLoading(it.getOrNull()?.curPage == it.getOrNull()?.pageCount)
                    ++mPage
                } else {
                    mHelper.trailingLoadState = LoadState.Error(it.exceptionOrNull()!!)
                }
            }
        }
    }

    override fun onRefresh() {
        mPage = 0
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

    companion object {
        fun newInstance() = HomeProjectsFragment()
    }
}