package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemMyBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemMyLineBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemMyMenuBinding

class MyItem(
    var itemType: Int = 0,
    var row: Int = 0,
    var title: String = "",
    var text: String = "",
    var background: Int = 0,
    var icon: Int = 0,
)

class MyAdapter : BaseMultiItemAdapter<MyItem>() {

    class LineVH(val binding: RecyclerItemMyLineBinding) : RecyclerView.ViewHolder(binding.root)
    class MenuVH(val binding: RecyclerItemMyMenuBinding) : RecyclerView.ViewHolder(binding.root)
    class ListVH(val binding: RecyclerItemMyBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        addItemType(0, object : OnMultiItemAdapterListener<MyItem, LineVH> {
            override fun onBind(holder: LineVH, position: Int, item: MyItem?) {
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): LineVH {
                return LineVH(RecyclerItemMyLineBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).addItemType(1, object : OnMultiItemAdapterListener<MyItem, MenuVH> {
            override fun onBind(holder: MenuVH, position: Int, item: MyItem?) {
                holder.binding.tvTitle.text = item?.title.orEmpty()
                holder.binding.tvText.text = item?.text.orEmpty()
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): MenuVH {
                return MenuVH(RecyclerItemMyMenuBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).addItemType(2, object : OnMultiItemAdapterListener<MyItem, ListVH> {
            override fun onBind(holder: ListVH, position: Int, item: MyItem?) {
                holder.binding.tvText.text = item?.title.orEmpty()
                holder.binding.tvDesc.text = item?.text.orEmpty()
                holder.binding.containerView.setBackgroundResource(item?.background!!)
                holder.binding.ivIcon.setImageResource(item.icon)
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ListVH {
                return ListVH(RecyclerItemMyBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).onItemViewType { position, list -> list[position].itemType }
    }

}