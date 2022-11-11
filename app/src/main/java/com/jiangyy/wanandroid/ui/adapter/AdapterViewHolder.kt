package com.jiangyy.wanandroid.ui.adapter

import android.view.View
import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class AdapterViewHolder(itemView: View) : BaseViewHolder(itemView) {

    fun setImageUrl(resId: Int, data: String?) {
        getView<ImageView>(resId).load(data)
    }

}