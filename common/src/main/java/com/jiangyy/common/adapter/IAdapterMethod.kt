package com.jiangyy.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

interface IAdapterMethod<T : Any, VB : ViewBinding> {

    fun convert(binding: VB, position: Int, item: T)

    fun createViewBinding(viewType: Int, inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean): VB

    fun getItem(position: Int): T

    fun setItem(position: Int, data: T)

    fun removeAt(position: Int)

    fun itemClick(block: (position: Int) -> Unit)

    fun itemLongClick(block: (position: Int) -> Unit)

    fun itemChildClick(block: (position: Int, view: View) -> Unit)

    fun addChildClick(vararg ids: View)

    var submitList: MutableList<T>?

}