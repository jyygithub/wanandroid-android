package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.jiangyy.common.adapter.BaseDiffItemCallback
import com.jiangyy.common.adapter.BaseViewHolder
import com.jiangyy.common.utils.milli2string
import com.jiangyy.wanandroid.databinding.RecyclerItemCoinHistoryBinding
import com.jiangyy.wanandroid.entity.CoinHistory

class CoinHistoryAdapter :
    PagingDataAdapter<CoinHistory, BaseViewHolder<RecyclerItemCoinHistoryBinding>>(BaseDiffItemCallback { it.date }) {

    override fun onBindViewHolder(holder: BaseViewHolder<RecyclerItemCoinHistoryBinding>, position: Int) {
        val item = peek(position)
        holder.binding.tvCoin.text = "+${item?.coinCount}"
        holder.binding.tvDate.text = item?.date?.milli2string()
        holder.binding.tvType.text = item?.reason
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RecyclerItemCoinHistoryBinding> {
        return BaseViewHolder(RecyclerItemCoinHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}