package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chad.library.adapter4.BaseQuickAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemRankingBinding
import com.jiangyy.wanandroid.entity.Coin

class RankingAdapter : BaseQuickAdapter<Coin, RankingAdapter.VH>() {

    class VH(val binding: RecyclerItemRankingBinding) : ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: Coin?) {
        if (position % 2 == 0) {
            holder.binding.viewParent.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.binding.viewParent.setBackgroundColor(Color.parseColor("#EBF5FB"))
        }
        holder.binding.tvRanking.text = "${item?.rank}"
        holder.binding.tvNickname.text = "${item?.username}"
        holder.binding.tvCoin.text = "${item?.coinCount}"
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(RecyclerItemRankingBinding.inflate(LayoutInflater.from(context), parent, false))
    }

}