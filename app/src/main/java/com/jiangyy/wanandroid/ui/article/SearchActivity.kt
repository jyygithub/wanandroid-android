package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.ArticleAdapter
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.ActivitySearchBinding
import com.jiangyy.wanandroid.entity.Article
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.core.click
import com.koonny.appcompat.core.orZero
import com.koonny.appcompat.module.StatusModule

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate),
    TrailingLoadStateAdapter.OnTrailingListener, SwipeRefreshLayout.OnRefreshListener, StatusModule {

    private var mPage = 0
    private val mAdapter = ArticleAdapter()
    private lateinit var mHelper: QuickAdapterHelper

    override fun viewBindStatus(): View {
        return binding.containerView.root
    }

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.tvCancel.click { finish() }

        mHelper = QuickAdapterHelper.Builder(mAdapter).setTrailingLoadStateAdapter(this).build()
        binding.containerView.recyclerView.adapter = mHelper.adapter
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(this, mAdapter.getItem(position))
        }
        binding.containerView.refreshLayout.setOnRefreshListener(this)
        binding.etInput.addTextChangedListener {
            if (it.isNullOrBlank()) {
                finishLoadingWithStatus("暂无数据", R.drawable.ic_state_empty)
            } else {
                onRefresh()
            }
        }
    }

    private fun pageHomeArticle() {
        flowRequest<ApiResponse.Paging<Article>> {
            request {
                if (mPage == 0) {
                    startLoading()
                }
                RetrofitHelper.getInstance().create(Api::class.java).search(mPage, binding.etInput.text.toString())
            }
            response {
                if (it.isSuccess) {
                    if (mPage == 0) {
                        binding.containerView.refreshLayout.isRefreshing = false
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
                    if (mPage == 0) {
                        finishLoadingWithStatus(it.exceptionOrNull()?.message.orEmpty(), R.drawable.ic_state_failure)
                    }
                    mHelper.trailingLoadState = LoadState.Error(it.exceptionOrNull()!!)
                }
            }
        }
    }

    override fun onRefresh() {
        mPage = 0
        pageHomeArticle()
    }

    override fun onStatusRetry() {
        mPage = 0
        pageHomeArticle()
    }

    override fun onFailRetry() {
        pageHomeArticle()
    }

    override fun onLoad() {
        pageHomeArticle()
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }

}