package com.jiangyy.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.entity.Tree

class TreeAdapter : BaseMultiItemQuickAdapter<Tree, BaseViewHolder>() {

    init {
        addItemType(0, R.layout.recycler_item_tree_parent)
        addItemType(1, R.layout.recycler_item_tree_child)
    }

    override fun convert(holder: BaseViewHolder, item: Tree) {
        when (holder.itemViewType) {
            0 -> {
                holder.setText(R.id.tvTitle, item.name)
            }
            1 -> {
                holder.setText(R.id.tvTitle, item.name)
            }
            else -> Unit
        }
    }
}