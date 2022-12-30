package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.core.millis2String
import com.jiangyy.viewbinding.adapter.BaseVBPagingDataAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemCoinHistoryBinding
import com.jiangyy.wanandroid.entity.CoinHistory

class CoinHistoryAdapter : BaseVBPagingDataAdapter<CoinHistory>({ it.date }) {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return RecyclerItemCoinHistoryBinding.inflate(inflater, parent, attachToParent)
    }

    override fun convert(_binding: ViewBinding, position: Int) {
        val binding = _binding as RecyclerItemCoinHistoryBinding
        val item = getItem(position)
        binding.tvCoin.text = "+${item?.coinCount}"
        binding.tvDate.text = item?.date?.millis2String()
        binding.tvType.text = item?.reason
    }

}