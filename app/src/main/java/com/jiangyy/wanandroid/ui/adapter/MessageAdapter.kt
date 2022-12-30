package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.core.millis2String
import com.jiangyy.core.orZero
import com.jiangyy.viewbinding.adapter.BaseVBDiffAdapter
import com.jiangyy.viewbinding.adapter.BaseVBPagingDataAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemMessageBinding
import com.jiangyy.wanandroid.entity.Message

class MessageAdapter : BaseVBPagingDataAdapter<Message>({ it.id }) {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return RecyclerItemMessageBinding.inflate(inflater, parent, attachToParent)
    }

    override fun convert(_binding: ViewBinding, position: Int) {
        val binding = _binding as RecyclerItemMessageBinding
        val item = getItem(position)
        binding.tvTag.text = item?.tag.orEmpty()
        binding.tvTime.text = item?.date.orZero().millis2String("yyyy-MM-dd HH:mm")
        binding.tvMessage.text = item?.message.orEmpty()
        binding.tvFromUser.text = "${item?.fromUser.orEmpty()} ${item?.title.orEmpty()}"
    }

}

class UnreadMessageAdapter : BaseVBDiffAdapter<Message>({ it.id }) {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return RecyclerItemMessageBinding.inflate(inflater, parent, attachToParent)
    }

    override fun convert(_binding: ViewBinding, position: Int) {
        val binding = _binding as RecyclerItemMessageBinding
        val item = getItem(position)
        binding.tvTag.text = item?.tag.orEmpty()
        binding.tvTime.text = item?.date.orZero().millis2String("yyyy-MM-dd HH:mm")
        binding.tvMessage.text = item?.message.orEmpty()
        binding.tvFromUser.text = "${item?.fromUser.orEmpty()} ${item?.title.orEmpty()}"
    }

}