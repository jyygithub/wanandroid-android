package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.core.millis2String
import com.jiangyy.core.orZero
import com.jiangyy.viewbinding.adapter.BaseVBPagingDataAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemTodoBinding
import com.jiangyy.wanandroid.entity.Todo

class TodoAdapter : BaseVBPagingDataAdapter<Todo>({ it.id }) {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return RecyclerItemTodoBinding.inflate(inflater, parent, attachToParent)
    }

    override fun convert(_binding: ViewBinding, position: Int) {
        val item = getItem(position)
        val binding = _binding as RecyclerItemTodoBinding
        binding.tvDate.text = item?.date.orZero().millis2String("yyyy-MM-dd")
        binding.tvContent.text = item?.title.orEmpty()
        binding.tvTitle.text = item?.content.orEmpty()
    }

}