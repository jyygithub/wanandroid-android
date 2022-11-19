package com.jiangyy.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.entity.Tree

class SubAdapter : BaseQuickAdapter<Tree, AdapterViewHolder>(R.layout.recycler_item_sub) {

    override fun convert(holder: AdapterViewHolder, item: Tree) {
        holder.setImageUrl(R.id.iv, item.cover)
    }
}