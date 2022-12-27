package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityCoinHistoryBinding
import com.jiangyy.wanandroid.ui.adapter.CoinHistoryAdapter

class CoinHistoryActivity : BaseLoadActivity<ActivityCoinHistoryBinding>(), MultipleStateModule {

    private val mAdapter = CoinHistoryAdapter()

    private val mViewModel by viewModels<CoinHistoryViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        binding.refreshLayout.setOnRefreshListener {
            mViewModel.firstLoad()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMore()
        }
        mViewModel.firstData().observe(this) {
            mAdapter.setList(null)
            binding.refreshLayout.isRefreshing = false
            if (it.datas.isEmpty()) {
                preLoadWithEmpty("暂无数据")
            } else {
                preLoadSuccess()
                mAdapter.addData(it.datas)
                if (mAdapter.data.size == it.total) {
                    mAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    mAdapter.loadMoreModule.loadMoreComplete()
                    mViewModel.mPage++
                }
            }
        }
        mViewModel.loadMoreData().observe(this) {
            if (it.datas.isEmpty()) {
                mAdapter.loadMoreModule.loadMoreEnd()
            } else {
                mAdapter.addData(it.datas)
                if (mAdapter.data.size == it.total) {
                    mAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    mAdapter.loadMoreModule.loadMoreComplete()
                    mViewModel.mPage++
                }
            }
        }
        mViewModel.dataError().observe(this) {
            if (it.second) {
                mAdapter.loadMoreModule.loadMoreFail()
            } else {
                binding.refreshLayout.isRefreshing = false
                preLoadWithFailure(it.first.message.orEmpty()) {
                    preLoad()
                }
            }
        }
    }

    override fun preLoad() {
        mViewModel.firstLoad()
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, CoinHistoryActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}