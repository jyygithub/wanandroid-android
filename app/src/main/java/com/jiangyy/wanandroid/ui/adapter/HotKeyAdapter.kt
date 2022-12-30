package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.viewbinding.adapter.BaseVBAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemHotKeyBinding
import com.jiangyy.wanandroid.entity.HotKey

class HotKeyAdapter : BaseVBAdapter<HotKey>() {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return RecyclerItemHotKeyBinding.inflate(inflater, parent, attachToParent)
    }

    override fun convert(_binding: ViewBinding, position: Int) {
        val binding = _binding as RecyclerItemHotKeyBinding
        binding.tv.text = getItem(position).name.orEmpty()
    }

}