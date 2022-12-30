package com.jiangyy.wanandroid.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.viewbinding.adapter.BaseVBPagingDataAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemRankingBinding
import com.jiangyy.wanandroid.entity.Coin

class RankingAdapter : BaseVBPagingDataAdapter<Coin>() {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return RecyclerItemRankingBinding.inflate(inflater, parent, attachToParent)
    }

    override fun convert(_binding: ViewBinding, position: Int) {
        val item = getItem(position)
        val binding = _binding as RecyclerItemRankingBinding
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