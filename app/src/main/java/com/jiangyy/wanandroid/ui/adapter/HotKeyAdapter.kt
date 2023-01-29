package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jiangyy.common.adapter.BaseAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemHotKeyBinding
import com.jiangyy.wanandroid.entity.HotKey

class HotKeyAdapter : BaseAdapter<HotKey, RecyclerItemHotKeyBinding>() {

    override fun convert(binding: RecyclerItemHotKeyBinding, position: Int, item: HotKey) {
        binding.tv.text = getItem(position).name.orEmpty()
    }

    override fun createViewBinding(
        viewType: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): RecyclerItemHotKeyBinding {
        return RecyclerItemHotKeyBinding.inflate(inflater, container, attachToParent)
    }

}