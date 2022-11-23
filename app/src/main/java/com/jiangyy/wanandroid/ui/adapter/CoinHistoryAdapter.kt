package com.jiangyy.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jiangyy.core.millis2String
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.entity.CoinHistory

class CoinHistoryAdapter : BaseQuickAdapter<CoinHistory, BaseViewHolder>(R.layout.recycler_item_coin_history), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: CoinHistory) {
        holder.setText(R.id.tvType, item.reason)
        holder.setText(R.id.tvDate, item.date?.millis2String())
        holder.setText(R.id.tvCoin, "+${item.coinCount}")
    }

}