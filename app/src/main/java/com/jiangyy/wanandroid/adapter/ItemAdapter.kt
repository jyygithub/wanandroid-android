package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ItemMyBinding

sealed class Item(open val title: String, open val content: String)
data class TopItem(override val title: String, override val content: String) : Item(title, content)
data class CenterItem(override val title: String, override val content: String) : Item(title, content)
data class BottomItem(override val title: String, override val content: String) : Item(title, content)
data class RoundItem(override val title: String, override val content: String) : Item(title, content)

class ItemAdapter : BaseQuickAdapter<Item, ItemAdapter.VH>() {

    class VH(val binding: ItemMyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: Item?) {
        if (item == null) return
        holder.binding.tvTitle.text = item.title
        holder.binding.tvContent.text = item.content
        when (item) {
            is TopItem -> {
                holder.binding.viewLine.isGone = true
                holder.binding.llContent.setBackgroundResource(R.drawable.selector_white_top_12)
                holder.binding.viewDivider.isGone = true
            }

            is CenterItem -> {
                holder.binding.viewLine.isGone = false
                holder.binding.llContent.setBackgroundResource(R.drawable.selector_white)
                holder.binding.viewDivider.isGone = true
            }

            is BottomItem -> {
                holder.binding.viewLine.isGone = false
                holder.binding.llContent.setBackgroundResource(R.drawable.selector_white_bottom_12)
                holder.binding.viewDivider.isGone = false
            }

            is RoundItem -> {
                holder.binding.viewLine.isGone = true
                holder.binding.llContent.setBackgroundResource(R.drawable.selector_white_12)
                holder.binding.viewDivider.isGone = false
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(ItemMyBinding.inflate(LayoutInflater.from(context), parent, false))
    }

}