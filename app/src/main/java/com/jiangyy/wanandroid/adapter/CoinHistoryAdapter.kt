package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chad.library.adapter.base.BaseQuickAdapter
import com.koonny.app.core.mills2string
import com.koonny.app.core.orZero
import com.jiangyy.wanandroid.databinding.RecyclerItemCoinHistoryBinding
import com.jiangyy.wanandroid.entity.CoinHistory

class CoinHistoryAdapter :BaseQuickAdapter<CoinHistory, CoinHistoryAdapter.VH>() {

    class VH(val binding : RecyclerItemCoinHistoryBinding) : ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: CoinHistory?) {
        holder.binding.tvCoin.text = "+${item?.coinCount}"
        holder.binding.tvDate.text = item?.date?.orZero()!!.mills2string()
        holder.binding.tvType.text = item?.reason
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(RecyclerItemCoinHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}