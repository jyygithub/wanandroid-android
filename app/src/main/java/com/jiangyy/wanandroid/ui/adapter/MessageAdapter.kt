package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.viewbinding.ViewBinding
import com.jiangyy.common.adapter.BasePagingDataAdapter
import com.jiangyy.common.adapter.BaseViewHolder
import com.jiangyy.common.utils.milli2string
import com.jiangyy.common.utils.orZero
import com.jiangyy.wanandroid.databinding.RecyclerItemMessageBinding
import com.jiangyy.wanandroid.entity.Message

class MessageAdapter : BasePagingDataAdapter<Message, RecyclerItemMessageBinding>({ it.id }) {

    override fun createViewBinding(
        viewType: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): RecyclerItemMessageBinding {
        return RecyclerItemMessageBinding.inflate(inflater, container, attachToParent)
    }

    override fun convert(binding: RecyclerItemMessageBinding, position: Int, item: Message?) {
        binding.tvTag.text = item?.tag.orEmpty()
        binding.tvTime.text = item?.date.orZero().milli2string("yyyy-MM-dd HH:mm")
        binding.tvMessage.text = item?.message.orEmpty()
        binding.tvFromUser.text = "${item?.fromUser.orEmpty()} ${item?.title.orEmpty()}"
    }

}

class UnreadMessageAdapter : BasePagingDataAdapter<Message, RecyclerItemMessageBinding>({ it.id }) {

    override fun createViewBinding(
        viewType: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): RecyclerItemMessageBinding {
        return RecyclerItemMessageBinding.inflate(inflater, container, attachToParent)
    }

    override fun convert(binding: RecyclerItemMessageBinding, position: Int, item: Message?) {
        binding.tvTag.text = item?.tag.orEmpty()
        binding.tvTime.text = item?.date.orZero().milli2string("yyyy-MM-dd HH:mm")
        binding.tvMessage.text = item?.message.orEmpty()
        binding.tvFromUser.text = "${item?.fromUser.orEmpty()} ${item?.title.orEmpty()}"
    }

}