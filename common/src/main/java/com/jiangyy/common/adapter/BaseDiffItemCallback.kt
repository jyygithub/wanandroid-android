package com.jiangyy.common.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class BaseDiffItemCallback<T : Any>(private val id: (T) -> Any? = { it }) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return id(oldItem) == id(newItem)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}