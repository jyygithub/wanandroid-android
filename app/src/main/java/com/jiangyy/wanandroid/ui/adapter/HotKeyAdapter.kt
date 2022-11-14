package com.jiangyy.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.entity.HotKey

class HotKeyAdapter : BaseQuickAdapter<HotKey, BaseViewHolder>(R.layout.recycler_item_hot_key) {

    override fun convert(holder: BaseViewHolder, item: HotKey) {
        holder.setText(R.id.tv, item.name.orEmpty())
    }

}