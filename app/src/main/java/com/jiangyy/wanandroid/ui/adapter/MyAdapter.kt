package com.jiangyy.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jiangyy.wanandroid.R

class MyItem(
    override val itemType: Int,
    var row: Int = 0,
    var title: String = "",
    var text: String = "",
    var background: Int = 0,
    var icon: Int = 0,
) : MultiItemEntity

class MyAdapter : BaseMultiItemQuickAdapter<MyItem, BaseViewHolder>() {

    init {
        addItemType(0, R.layout.recycler_item_my_line)
        addItemType(1, R.layout.recycler_item_my_menu)
        addItemType(2, R.layout.recycler_item_my)
    }

    override fun convert(holder: BaseViewHolder, item: MyItem) {
        when (holder.itemViewType) {
            1 -> {
                holder.setText(R.id.tvTitle, item.title)
                holder.setText(R.id.tvText, item.text)
            }
            2 -> {
                holder.setText(R.id.tvText, item.title)
                holder.setBackgroundResource(R.id.containerView, item.background)
                holder.setImageResource(R.id.ivIcon, item.icon)
            }
        }
    }

}