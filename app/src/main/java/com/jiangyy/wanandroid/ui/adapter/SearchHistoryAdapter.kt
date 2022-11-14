package com.jiangyy.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jiangyy.wanandroid.R

class SearchHistoryAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.recycler_item_search_history) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv, item)
    }

}