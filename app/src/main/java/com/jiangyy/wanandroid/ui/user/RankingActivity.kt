package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.jiangyy.common.view.BaseLoadActivity
import com.jiangyy.common.adapter.FooterAdapter
import com.jiangyy.wanandroid.databinding.ActivityRankingBinding
import com.jiangyy.wanandroid.ui.adapter.RankingAdapter
import kotlinx.coroutines.launch

class RankingActivity : BaseLoadActivity<ActivityRankingBinding>(ActivityRankingBinding::inflate) {

    override val viewBindStatus: View get() = binding.refreshLayout

    private val mAdapter = RankingAdapter()

    override fun initWidget() {
        super.initWidget()
        binding.recyclerView.adapter = mAdapter.withLoadStateFooter(
            FooterAdapter { mAdapter.retry() }
        )
        mAdapter.addLoadStateListener {
            binding.refreshLayout.isRefreshing = false
            when (it.refresh) {
                is LoadState.NotLoading -> preLoadSuccess()
//                is LoadState.Loading -> preLoading()
                is LoadState.Error -> preLoadError {
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
        super.preLoad()
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