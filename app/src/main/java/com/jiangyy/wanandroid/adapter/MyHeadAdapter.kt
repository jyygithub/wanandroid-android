package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.BaseSingleItemAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.jiangyy.wanandroid.R

class MyHeadAdapter : BaseSingleItemAdapter<Any, QuickViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        return QuickViewHolder(R.layout.item_my_head, parent)
    }

    override fun onBindViewHolder(holder: QuickViewHolder, item: Any?) {

    }
}