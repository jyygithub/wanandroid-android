package com.jiangyy.wanandroid.ui.coin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.module.StatusModule
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.CoinHistoryAdapter
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.ApiResponse
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.ActivityCoinHistoryBinding
import com.jiangyy.wanandroid.entity.CoinHistory

class CoinHistoryActivity : BaseActivity<ActivityCoinHistoryBinding>(ActivityCoinHistoryBinding::inflate),
    TrailingLoadStateAdapter.OnTrailingListener, SwipeRefreshLayout.OnRefreshListener {

    private var mPage = 1
    private val mAdapter = CoinHistoryAdapter()
    private lateinit var mHelper: QuickAdapterHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = QuickAdapterHelper.Builder(mAdapter).setTrailingLoadStateAdapter(this).build()
        binding.recyclerView.adapter = mHelper.adapter
        binding.refreshLayout.setOnRefreshListener(this)
        onRefresh()
    }

    private fun pageHomeArticle() {
        flowRequest<ApiResponse.Paging<CoinHistory>> {
            request {
                if (mPage == 1) {
                    startLoading()
                }
                RetrofitHelper.getInstance().create(Api::class.java).pageCoinHistory(mPage)
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
                    if (mPage == 1) {
                        finishLoadingWithStatus(it.exceptionOrNull()?.message.orEmpty(), R.drawable.ic_state_failure)
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

    override fun onStatusRetry(exception: Exception) {
        mPage = 1
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
        fun actionStart(context: Context) {
            Intent(context, CoinHistoryActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}