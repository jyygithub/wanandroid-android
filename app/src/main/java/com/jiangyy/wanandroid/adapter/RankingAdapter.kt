package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.jiangyy.wanandroid.databinding.ItemRankingBinding
import com.jiangyy.wanandroid.entity.Ranking

class RankingAdapter : BaseQuickAdapter<Ranking, RankingAdapter.VH>() {

    class VH(val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: Ranking?) {
        holder.binding.tvPosition.text = item?.rank
        holder.binding.tvUsername.text = item?.username
        holder.binding.tvCoin.text = "${item?.coinCount}"
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRankingBinding.inflate(LayoutInflater.from(context), parent, false))
    }
}