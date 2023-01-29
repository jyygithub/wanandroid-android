package com.jiangyy.common.utils

import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.click(interval: Long = 1000L, listener: () -> Unit) {
    this.setOnClickListener(object : OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View?) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > interval) {
                listener.invoke()
                lastClickTime = System.currentTimeMillis()
            }
        }
    })
}

fun TextView.setTextColorRes(@ColorRes id: Int) = setTextColor(ContextCompat.getColor(context, id))

fun ImageView.setMipMap(filename: String?) {
    if (filename.isNullOrBlank()) return
    this.setImageResource(this.context.resources.getIdentifier(filename, "mipmap", this.context.packageName))
}