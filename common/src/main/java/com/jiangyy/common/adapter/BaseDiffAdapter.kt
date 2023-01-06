package com.jiangyy.common.adapter

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListDiffer.ListListener
import androidx.viewbinding.ViewBinding

abstract class BaseDiffAdapter<T : Any, VB : ViewBinding>(id: (T) -> Any? = { it }) : BaseAdapter<T, VB>() {

    private val mDiffer: AsyncListDiffer<T>
    private val mListener =
        ListListener { previousList, currentList ->
            this@BaseDiffAdapter.onCurrentListChanged(
                previousList,
                currentList
            )
        }

    init {
        mDiffer = AsyncListDiffer(
            AdapterListUpdateCallback(this),
            AsyncDifferConfig.Builder(BaseDiffItemCallback(id)).build()
        )
        mDiffer.addListListener(mListener)
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    override fun getItem(position: Int): T = mDiffer.currentList[position]

    override fun setItem(position: Int, data: T) {
        mDiffer.currentList[position] = data
        notifyItemChanged(position)
        notifyItemRangeChanged(0, mDiffer.currentList.size)
    }

    override fun removeAt(position: Int) {
        mDiffer.currentList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mDiffer.currentList.size)
    }

    override var submitList: MutableList<T>?
        set(value) {
            mDiffer.submitList(value ?: mutableListOf())
        }
        get() = mDiffer.currentList

    open fun onCurrentListChanged(previousList: MutableList<T>, currentList: MutableList<T>) {}

}