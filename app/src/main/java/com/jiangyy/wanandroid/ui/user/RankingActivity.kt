package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.adapter.FooterAdapter
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityRankingBinding
import com.jiangyy.wanandroid.ui.adapter.RankingAdapter
import kotlinx.coroutines.launch

class RankingActivity : BaseLoadActivity<ActivityRankingBinding>(), MultipleStateModule {

    private val mAdapter = RankingAdapter()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter.withLoadStateFooter(
            FooterAdapter { mAdapter.retry() }
        )
        mAdapter.addLoadStateListener {
            binding.refreshLayout.isRefreshing = false
            when (it.refresh) {
                is LoadState.NotLoading -> preLoadSuccess()
//                is LoadState.Loading -> preLoading()
                is LoadState.Error -> preLoadWithFailure {
                    binding.recyclerView.swapAdapter(mAdapter, true)
                    mAdapter.refresh()
                }

                else -> Unit
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.recyclerView.swapAdapter(mAdapter, true)
            mAdapter.refresh()
        }
    }

    override fun preLoad() {
        val viewModel by viewModels<RankingViewModel>()
        lifecycleScope.launch {
            viewModel.ranking().collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, RankingActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}