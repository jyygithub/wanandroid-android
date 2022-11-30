package com.jiangyy.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.jiangyy.core.millis2String
import com.jiangyy.core.orZero
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.entity.Message

class MessageAdapter : BaseQuickAdapter<Message, AdapterViewHolder>(R.layout.recycler_item_message) {

    override fun convert(holder: AdapterViewHolder, item: Message) {
        holder.setText(R.id.tvTag, item.tag.orEmpty())
        holder.setText(R.id.tvTime, item.date.orZero().millis2String("yyyy-MM-dd HH:mm"))
        holder.setText(R.id.tvMessage, item.message.orEmpty())
        holder.setText(R.id.tvFromUser, "${item.fromUser.orEmpty()} ${item.title.orEmpty()}")
    }

}