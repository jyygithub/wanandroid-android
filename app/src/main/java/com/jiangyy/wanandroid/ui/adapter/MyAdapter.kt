package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.common.adapter.BaseAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemMyBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemMyLineBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemMyMenuBinding

class MyItem(
    var viewType: Int,
    var row: Int = 0,
    var title: String = "",
    var text: String = "",
    var background: Int = 0,
    var icon: Int = 0,
)

class MyAdapter : BaseAdapter<MyItem, ViewBinding>() {

    override fun convert(binding: ViewBinding, position: Int, item: MyItem) {
        when (getItemViewType(position)) {
            1 -> {
                binding as RecyclerItemMyMenuBinding
                binding.tvTitle.text = item.title.orEmpty()
                binding.tvText.text = item.text.orEmpty()
            }

            2 -> {
                binding as RecyclerItemMyBinding
                binding.tvText.text = item.title.orEmpty()
                binding.containerView.setBackgroundResource(item.background)
                binding.ivIcon.setImageResource(item.icon)
            }
        }
    }

    override fun createViewBinding(viewType: Int, inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean): ViewBinding {
        return when (viewType) {
            0 -> RecyclerItemMyLineBinding.inflate(inflater, container, attachToParent)
            1 -> RecyclerItemMyMenuBinding.inflate(inflater, container, attachToParent)
            2 -> RecyclerItemMyBinding.inflate(inflater, container, attachToParent)
            else -> RecyclerItemMyLineBinding.inflate(inflater, container, attachToParent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

}