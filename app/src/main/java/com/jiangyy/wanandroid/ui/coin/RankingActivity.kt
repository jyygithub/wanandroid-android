package com.jiangyy.wanandroid.ui.coin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.RankingAdapter
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityRankingBinding

class RankingActivity : BaseActivity<ActivityRankingBinding>(ActivityRankingBinding::inflate),
    TrailingLoadStateAdapter.OnTrailingListener, SwipeRefreshLayout.OnRefreshListener {

    private var mPage = 1
    private val mAdapter = RankingAdapter()
    private lateinit var mHelper: QuickAdapterHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = QuickAdapterHelper.Builder(mAdapter).setTrailingLoadStateAdapter(this).build()
        binding.recyclerView.adapter = mHelper.adapter
        binding.refreshLayout.setOnRefreshListener(this)
        onRefresh()
    }

    private fun pageHomeArticle() {
        flowRequest<ApiResponse.Paging<Coin>> {
            request {
                if (mPage == 1) {
                    startLoading()
                }
                RetrofitHelper.getInstance().create(Api::class.java).ranking(mPage)
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
            context.startActivity(Intent(context, RankingActivity::class.java))
        }
    }

}