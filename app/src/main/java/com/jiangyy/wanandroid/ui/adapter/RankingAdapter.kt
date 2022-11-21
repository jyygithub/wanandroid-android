package com.jiangyy.wanandroid.ui.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.entity.Coin

class RankingAdapter : BaseQuickAdapter<Coin, AdapterViewHolder>(R.layout.recycler_item_ranking), LoadMoreModule {

    override fun convert(holder: AdapterViewHolder, item: Coin) {
        if (holder.bindingAdapterPosition % 2 == 0) {
            holder.setBackgroundColor(R.id.viewParent, Color.parseColor("#FFFFFF"))
        } else {
            holder.setBackgroundColor(R.id.viewParent, Color.parseColor("#EBF5FB"))
        }
        holder.setText(R.id.tvRanking, "${item.rank}")
        holder.setText(R.id.tvNickname, item.username)
        holder.setText(R.id.tvCoin, "${item.coinCount}")
    }

}