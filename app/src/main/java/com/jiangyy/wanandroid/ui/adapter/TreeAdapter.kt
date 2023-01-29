package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.common.adapter.BaseAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemTreeChildBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemTreeParentBinding
import com.jiangyy.wanandroid.entity.Tree

class TreeAdapter : BaseAdapter<Tree, ViewBinding>() {

    override fun convert(binding: ViewBinding, position: Int, item: Tree) {
        if (getItemViewType(position) == 0) {
            val bind = binding as RecyclerItemTreeParentBinding
            bind.tvTitle.text = getItem(position).name
        } else {
            val bind = binding as RecyclerItemTreeChildBinding
            bind.tvTitle.text = getItem(position).name
        }
    }

    override fun createViewBinding(viewType: Int, inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean): ViewBinding {
        return if (viewType == 0) {
            RecyclerItemTreeParentBinding.inflate(inflater, container, attachToParent)
        } else {
            RecyclerItemTreeChildBinding.inflate(inflater, container, attachToParent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

}