package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ItemMyContentBinding

sealed class MyItem(
    open val icon: Int,
    open val title: String,
    open val content: String,
)

class MyRoundItem(override val icon: Int, override val title: String, override val content: String) :
    MyItem(icon, title, content)

class MyTopItem(override val icon: Int, override val title: String, override val content: String) :
    MyItem(icon, title, content)

class MyCenterItem(override val icon: Int, override val title: String, override val content: String) :
    MyItem(icon, title, content)

class MyBottomItem(override val icon: Int, override val title: String, override val content: String) :
    MyItem(icon, title, content)

class MyContentAdapter : BaseQuickAdapter<MyItem, MyContentAdapter.VH>() {


    class VH(val binding: ItemMyContentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(ItemMyContentBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: MyItem?) {
        if (item == null) return
        when (item) {
            is MyTopItem -> {
                holder.binding.space.visibility = View.VISIBLE
                holder.binding.viewDivder.visibility = View.VISIBLE
                holder.binding.ll.setBackgroundResource(R.drawable.selector_white_top_12)
            }

            is MyCenterItem -> {
                holder.binding.space.visibility = View.GONE
                holder.binding.viewDivder.visibility = View.VISIBLE
                holder.binding.ll.setBackgroundResource(R.drawable.selector_white)
            }

            is MyBottomItem -> {
                holder.binding.space.visibility = View.GONE
                holder.binding.viewDivder.visibility = View.GONE
                holder.binding.ll.setBackgroundResource(R.drawable.selector_white_bottom_12)
            }

            is MyRoundItem -> {
                holder.binding.space.visibility = View.VISIBLE
                holder.binding.viewDivder.visibility = View.GONE
                holder.binding.ll.setBackgroundResource(R.drawable.selector_white_12)
            }
        }
        holder.binding.tvTitle.text = item.title
        holder.binding.tvContent.text = item.content
        holder.binding.tvTitle.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(context, item.icon), null, null, null
        )
    }
}