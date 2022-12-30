package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jiangyy.viewbinding.adapter.BaseVBAdapter
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

class MyAdapter : BaseVBAdapter<MyItem>() {

    override fun onCreateViewBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ViewBinding {
        return when (viewType) {
            0 -> RecyclerItemMyLineBinding.inflate(inflater, parent, attachToParent)
            1 -> RecyclerItemMyMenuBinding.inflate(inflater, parent, attachToParent)
            2 -> RecyclerItemMyBinding.inflate(inflater, parent, attachToParent)
            else -> RecyclerItemMyLineBinding.inflate(inflater, parent, attachToParent)
        }
    }

    override fun convert(_binding: ViewBinding, position: Int) {
        val item = getItem(position)
        when (getItemViewType(position)) {
            1 -> {
                val binding = _binding as RecyclerItemMyMenuBinding
                binding.tvTitle.text = item?.title.orEmpty()
                binding.tvText.text = item?.text.orEmpty()
            }

            2 -> {
                val binding = _binding as RecyclerItemMyBinding
                binding.tvText.text = item?.title.orEmpty()
                binding.containerView.setBackgroundResource(item.background)
                binding.ivIcon.setImageResource(item.icon)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

}