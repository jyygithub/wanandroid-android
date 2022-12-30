package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.viewbinding.adapter.BaseVBAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemSearchHistoryBinding

class SearchHistoryAdapter : BaseVBAdapter<String>() {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return RecyclerItemSearchHistoryBinding.inflate(inflater, parent, attachToParent)
    }

    override fun convert(_binding: ViewBinding, position: Int) {
        val binding = _binding as RecyclerItemSearchHistoryBinding
        binding.tv.text = getItem(position)
    }

}