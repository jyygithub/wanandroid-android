package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.viewbinding.adapter.BaseVBAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemTreeChildBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemTreeParentBinding
import com.jiangyy.wanandroid.entity.Tree

class TreeAdapter : BaseVBAdapter<Tree>() {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return if (viewType == 0) {
            RecyclerItemTreeParentBinding.inflate(inflater, parent, attachToParent)
        } else {
            RecyclerItemTreeChildBinding.inflate(inflater, parent, attachToParent)
        }
    }

    override fun convert(_binding: ViewBinding, position: Int) {

        if (getItemViewType(position) == 0) {
            val binding = _binding as RecyclerItemTreeParentBinding
            binding.tvTitle.text = getItem(position).name
        } else {
            val binding = _binding as RecyclerItemTreeChildBinding
            binding.tvTitle.text = getItem(position).name
        }

    }

}