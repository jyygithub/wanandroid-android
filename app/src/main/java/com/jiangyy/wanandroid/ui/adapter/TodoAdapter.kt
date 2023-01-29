package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jiangyy.common.adapter.BasePagingDataAdapter
import com.jiangyy.common.utils.milli2string
import com.jiangyy.common.utils.orZero
import com.jiangyy.wanandroid.databinding.RecyclerItemTodoBinding
import com.jiangyy.wanandroid.entity.Todo

class TodoAdapter : BasePagingDataAdapter<Todo, RecyclerItemTodoBinding>({ it.id }) {

    override fun createViewBinding(
        viewType: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): RecyclerItemTodoBinding {
        return RecyclerItemTodoBinding.inflate(inflater, container, attachToParent)
    }

    override fun convert(binding: RecyclerItemTodoBinding, position: Int, item: Todo?) {
        binding.tvDate.text = item?.date.orZero().milli2string("yyyy-MM-dd")
        binding.tvContent.text = item?.title.orEmpty()
        binding.tvTitle.text = item?.content.orEmpty()
    }

}