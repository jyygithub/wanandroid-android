package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.jiangyy.common.adapter.BaseAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemSubBinding
import com.jiangyy.wanandroid.entity.Tree

class SubAdapter : BaseAdapter<Tree, RecyclerItemSubBinding>() {

    override fun convert(binding: RecyclerItemSubBinding, position: Int, item: Tree) {
        binding.iv.load(getItem(position).cover)
    }

    override fun createViewBinding(
        viewType: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): RecyclerItemSubBinding {
        return RecyclerItemSubBinding.inflate(inflater, container, attachToParent)
    }

}