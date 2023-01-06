package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jiangyy.common.adapter.BaseAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemSearchHistoryBinding

class SearchHistoryAdapter : BaseAdapter<String, RecyclerItemSearchHistoryBinding>() {

    override fun convert(binding: RecyclerItemSearchHistoryBinding, position: Int, item: String) {
        binding.tv.text = getItem(position)
    }

    override fun createViewBinding(
        viewType: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): RecyclerItemSearchHistoryBinding {
        return RecyclerItemSearchHistoryBinding.inflate(inflater, container, attachToParent)
    }

}