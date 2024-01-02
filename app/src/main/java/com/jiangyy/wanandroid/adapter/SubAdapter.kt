package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemSubBinding
import com.jiangyy.wanandroid.entity.Tree

class SubAdapter : BaseQuickAdapter<Tree, SubAdapter.VH>() {

    class VH(val binding: RecyclerItemSubBinding) : ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: Tree?) {
        holder.binding.iv.load(getItem(position)?.cover)
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(RecyclerItemSubBinding.inflate(LayoutInflater.from(context), parent, false))
    }

}