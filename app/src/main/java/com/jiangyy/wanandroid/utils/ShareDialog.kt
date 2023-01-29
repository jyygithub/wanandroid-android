package com.jiangyy.wanandroid.utils

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jiangyy.common.adapter.BaseAdapter
import com.jiangyy.dialog.BaseDialogFragment
import com.jiangyy.wanandroid.databinding.DialogShareBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemShareBinding

class ShareDialog : BaseDialogFragment<DialogShareBinding>(DialogShareBinding::inflate) {

    override val dialogGravity: Int get() = Gravity.BOTTOM

    private val mAdapter = object : BaseAdapter<String, RecyclerItemShareBinding>() {
        override fun convert(binding: RecyclerItemShareBinding, position: Int, item: String) {
            binding.tv.text = item
        }

        override fun createViewBinding(
            viewType: Int,
            inflater: LayoutInflater,
            container: ViewGroup?,
            attachToParent: Boolean
        ): RecyclerItemShareBinding {
            return RecyclerItemShareBinding.inflate(inflater, container, attachToParent)
        }
    }

    private var mBlock: ((Int) -> Unit)? = null

    fun success(block: (Int) -> Unit): ShareDialog {
        mBlock = block
        return this
    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        mAdapter.submitList = mutableListOf("收藏", "复制链接", "浏览器打开", "刷新", "微信", "朋友圈", "QQ", "QQ空间", "微信收藏")
        mAdapter.itemClick {
            mBlock?.invoke(it)
            dismiss()
        }
    }

}