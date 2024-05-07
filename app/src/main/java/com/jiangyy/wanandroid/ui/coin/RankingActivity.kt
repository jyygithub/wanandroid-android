package com.jiangyy.wanandroid.ui.coin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.jiangyy.wanandroid.adapter.RankingAdapter
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityRankingBinding
import com.jiangyy.wanandroid.ktor.CoinApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {

            flow {
                emit(CoinApi().ranking(1))
            }.catch { }.collect {
                mAdapter.submitList(it.data.datas)
            }

        }
    }

    override fun onRefresh() {
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