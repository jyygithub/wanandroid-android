package com.jiangyy.dialog

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import com.jiangyy.dialog.databinding.DialogDoneBinding

class DoneDialog : BaseDialogFragment<DialogDoneBinding>(DialogDoneBinding::inflate) {

    private var mContentLayout: Int? = null

    private var mIds: IntArray? = null

    fun inflate(@LayoutRes contentLayout: Int): DoneDialog {
        mContentLayout = contentLayout
        return this
    }

    fun dismissView(vararg ids: Int): DoneDialog {
        mIds = ids
        return this
    }

    override val dialogStyle: Int
        get() = R.style.Theme_Jiang_Dialog_Bottom


    override fun initValue() {
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun initWidget() {
        val v = LayoutInflater.from(context).inflate(mContentLayout!!, binding.frameLayout, true)
        mIds?.forEach {
            v.findViewById<View>(it).setOnClickListener { dismiss() }
        }
    }

    override val dialogWidth: Float
        get() = 1f

    override val dialogHeight: Float
        get() = 1f

}