package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemTreeChildBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemTreeParentBinding
import com.jiangyy.wanandroid.entity.Tree

class TreeAdapter : BaseMultiItemAdapter<Tree>() {

    class ParentVH(val binding: RecyclerItemTreeParentBinding) : ViewHolder(binding.root)
    class ChildVH(val binding: RecyclerItemTreeChildBinding) : ViewHolder(binding.root)

    init {
        addItemType(0, object : OnMultiItemAdapterListener<Tree, ParentVH> {
            override fun onBind(holder: ParentVH, position: Int, item: Tree?) {
                holder.binding.tvTitle.text = item?.name
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ParentVH {
                return ParentVH(RecyclerItemTreeParentBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).addItemType(1, object : OnMultiItemAdapterListener<Tree, ChildVH> {
            override fun onBind(holder: ChildVH, position: Int, item: Tree?) {
                holder.binding.tvTitle.text = item?.name
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ChildVH {
                return ChildVH(RecyclerItemTreeChildBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).onItemViewType { position, list ->
            if ((list[position].parentChapterId ?: 0) < 1) 0 else 1
        }
    }

}