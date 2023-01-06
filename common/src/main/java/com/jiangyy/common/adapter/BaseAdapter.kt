package com.jiangyy.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jiangyy.common.utils.click

abstract class BaseAdapter<T : Any, VB : ViewBinding> : RecyclerView.Adapter<BaseViewHolder<VB>>(),
    IAdapterMethod<T, VB> {

    private var mCurrentList = mutableListOf<T>()
    private var mItemBlock: ((position: Int) -> Unit)? = null
    private var mItemLongBlock: ((position: Int) -> Unit)? = null
    private var mItemChildBlock: ((position: Int, view: View) -> Unit)? = null
    private var mChildViews = emptyList<View>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        return BaseViewHolder(createViewBinding(viewType, inflater, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        convert(holder.binding, position, getItem(position))
        holder.itemView.click(100L) { mItemBlock?.invoke(holder.bindingAdapterPosition) }
        holder.itemView.setOnLongClickListener {
            mItemLongBlock?.invoke(holder.bindingAdapterPosition)
            false
        }
        mChildViews.forEach {
            it.click(100L) {
                mItemChildBlock?.invoke(holder.bindingAdapterPosition, it)
            }
        }
    }

    override fun getItemCount(): Int = mCurrentList.size

    override fun getItem(position: Int): T = mCurrentList[position]

    override fun setItem(position: Int, data: T) {
        mCurrentList[position] = data
        notifyItemChanged(position)
        notifyItemRangeChanged(0, mCurrentList.size)
    }

    override fun removeAt(position: Int) {
        mCurrentList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mCurrentList.size)
    }

    override fun itemClick(block: (Int) -> Unit) {
        mItemBlock = block
    }

    override fun itemLongClick(block: (Int) -> Unit) {
        mItemLongBlock = block
    }

    override fun itemChildClick(block: (position: Int, view: View) -> Unit) {
        mItemChildBlock = block
    }

    override fun addChildClick(vararg ids: View) {
        mChildViews = ids.toList()
    }

    override var submitList: MutableList<T>?
        set(value) {
            mCurrentList = value ?: mutableListOf()
            notifyItemRangeChanged(0, mCurrentList.size)
        }
        get() = mCurrentList
}