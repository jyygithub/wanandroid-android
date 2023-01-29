package com.jiangyy.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.viewbinding.ViewBinding
import com.jiangyy.common.utils.click

abstract class BasePagingDataAdapter<T : Any, VB : ViewBinding>(id: (T) -> Any? = { it }) :
    PagingDataAdapter<T, BaseViewHolder<VB>>(BaseDiffItemCallback(id)) {

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

    fun itemClick(block: (Int) -> Unit) {
        mItemBlock = block
    }

    fun itemLongClick(block: (Int) -> Unit) {
        mItemLongBlock = block
    }

    fun itemChildClick(block: (position: Int, view: View) -> Unit) {
        mItemChildBlock = block
    }

    fun addChildClick(vararg ids: View) {
        mChildViews = ids.toList()
    }

    abstract fun createViewBinding(viewType: Int, inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean): VB

    abstract fun convert(binding: VB, position: Int, item: T?)

}