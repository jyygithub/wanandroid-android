package com.jiangyy.wanandroid.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jiangyy.common.adapter.BasePagingDataAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemRankingBinding
import com.jiangyy.wanandroid.entity.Coin

class RankingAdapter : BasePagingDataAdapter<Coin, RecyclerItemRankingBinding>() {

    override fun createViewBinding(
        viewType: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): RecyclerItemRankingBinding {
        return RecyclerItemRankingBinding.inflate(inflater, container, attachToParent)
    }

    override fun convert(binding: RecyclerItemRankingBinding, position: Int, item: Coin?) {
        if (position % 2 == 0) {
            binding.viewParent.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            binding.viewParent.setBackgroundColor(Color.parseColor("#EBF5FB"))
        }
        binding.tvRanking.text = "${item?.rank}"
        binding.tvNickname.text = "${item?.username}"
        binding.tvCoin.text = "${item?.coinCount}"
    }

}