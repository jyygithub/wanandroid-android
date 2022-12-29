package com.jiangyy.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.jiangyy.core.millis2String
import com.jiangyy.core.orZero
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.entity.Todo

class TodoAdapter : BaseQuickAdapter<Todo, AdapterViewHolder>(R.layout.recycler_item_todo), LoadMoreModule {

    override fun convert(holder: AdapterViewHolder, item: Todo) {
        holder.setText(R.id.tvDate, item.date.orZero().millis2String("yyyy-MM-dd"))
        holder.setText(R.id.tvTitle, item.title.orEmpty())
        holder.setText(R.id.tvContent, item.content.orEmpty())
    }

}