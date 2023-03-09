package com.jiangyy.wanandroid.ui.article

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.koonny.appcompat.BaseFragment
import com.koonny.appcompat.core.orZero
import com.koonny.appcompat.module.StatusModule
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.ArticleAdapter
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.FragmentArticlesBinding
import com.jiangyy.wanandroid.entity.ShareHistory

/**
 * 分享记录
 */
class ArticleInShareFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate),
    TrailingLoadStateAdapter.OnTrailingListener, SwipeRefreshLayout.OnRefreshListener, StatusModule {

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
        flowRequest<ShareHistory> {
            request {
                if (mPage == 1) {
                    startLoading()
                }
                RetrofitHelper.getInstance().create(Api::class.java).listShareHistory(mPage)
            }
            response {
                if (it.isSuccess) {
                    if (it.getOrNull()?.shareArticles?.curPage == 1) {
                        binding.refreshLayout.isRefreshing = false
                        mAdapter.submitList(it.getOrNull()?.shareArticles?.datas)
                        if (it.getOrNull()?.shareArticles?.datas.isNullOrEmpty()) {
                            finishLoadingWithStatus("暂无数据", R.drawable.ic_state_empty)
                        } else {
                            finishLoading()
                        }
                    } else {
                        it.getOrNull()?.shareArticles?.datas?.let { it1 -> mAdapter.addAll(it1) }
                    }
                    mHelper.trailingLoadState = LoadState.NotLoading(
                        it.getOrNull()?.shareArticles?.curPage.orZero() >= it.getOrNull()?.shareArticles?.pageCount.orZero()
                    )
                    ++mPage
                } else {
                    if (mPage == 1) {
                        finishLoadingWithStatus("加载失败", R.drawable.ic_state_failure)
                    }
                    mHelper.trailingLoadState = LoadState.Error(it.exceptionOrNull()!!)
                }
            }
        }
    }

    override fun onRefresh() {
        mPage = 1
        pageHomeArticle()
    }

    override fun onStatusRetry() {
        mPage = 1
        pageHomeArticle()
    }

    override fun onFailRetry() {
        pageHomeArticle()
    }

    override fun onLoad() {
        pageHomeArticle()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInShareFragment()
    }

}