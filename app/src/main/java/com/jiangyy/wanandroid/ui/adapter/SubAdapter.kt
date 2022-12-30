package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.jiangyy.viewbinding.adapter.BaseVBAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemSubBinding
import com.jiangyy.wanandroid.entity.Tree

class SubAdapter : BaseVBAdapter<Tree>() {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return RecyclerItemSubBinding.inflate(inflater, parent, attachToParent)
    }

    override fun convert(_binding: ViewBinding, position: Int) {
        val binding = _binding as RecyclerItemSubBinding
        binding.iv.load(getItem(position).cover)
    }

}