package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.adapter.RankingAdapter
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityRankingBinding
import com.jiangyy.wanandroid.ktor.CoinApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RankingActivity : BaseActivity<ActivityRankingBinding>(ActivityRankingBinding::inflate) {

    private val mAdapter = RankingAdapter()

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.recyclerView.adapter = mAdapter
    }

    override fun onPrepareData() {
        super.onPrepareData()
        lifecycleScope.launch {
            flow {
                emit(CoinApi().ranking(1))
            }.catch { }.collect {
                mAdapter.submitList(it.data.datas)
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RankingActivity::class.java))
        }
    }

}