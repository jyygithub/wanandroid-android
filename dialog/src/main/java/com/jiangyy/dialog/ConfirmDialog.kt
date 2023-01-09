package com.jiangyy.dialog

import android.view.View
import androidx.core.content.ContextCompat
import com.jiangyy.dialog.databinding.DialogConfirmBinding
import com.jiangyy.dialog.utils.asBLRadii
import com.jiangyy.dialog.utils.asBRRadii
import com.jiangyy.dialog.utils.asBottomRadii
import com.jiangyy.dialog.utils.isGone

class ConfirmDialog : BaseDialogFragment<DialogConfirmBinding>(DialogConfirmBinding::inflate) {

    private var mConfirmClick: View.OnClickListener? = null
    private var mCancelClick: View.OnClickListener? = null

    fun confirm(listener: View.OnClickListener?): ConfirmDialog {
        mConfirmClick = listener
        return this
    }

    fun cancel(listener: View.OnClickListener?): ConfirmDialog {
        mCancelClick = listener
        return this
    }

    override val dialogWidth: Float
        get() = 0.78f

    class Config {
        var width: Float = 0f
        var height: Float = 0f
        var title: CharSequence? = null
        var content: CharSequence? = null
        var confirmText: CharSequence? = "确定"
        var confirmTextColor: Int? = null
        var cancelTextColor: Int? = null
        var cancelText: CharSequence? = "取消"
        var dismissOnBackPressed: Boolean = true
        var dismissOnTouchOutside: Boolean = true
    }

    private var mConfig = Config()

    fun bindConfig(block: Config.() -> Unit): ConfirmDialog {
        block.invoke(mConfig)
        return this
    }

    override fun initValue() {
        dialog?.setCancelable(mConfig.dismissOnBackPressed)
        dialog?.setCanceledOnTouchOutside(mConfig.dismissOnTouchOutside)
    }

    override fun initWidget() {
        binding.btnCancel.setOnClickListener {
            mCancelClick?.onClick(it)
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            mConfirmClick?.onClick(it)
            dismiss()
        }
        binding.tvTitle.isGone = mConfig.title.isNullOrBlank()
        binding.tvContent.isGone = mConfig.content.isNullOrBlank()
        binding.btnConfirm.isGone = mConfig.confirmText.isNullOrBlank()
        binding.btnCancel.isGone = mConfig.cancelText.isNullOrBlank()
        binding.tvTitle.text = mConfig.title
        binding.tvContent.text = mConfig.content
        binding.btnConfirm.text = mConfig.confirmText
        binding.btnCancel.text = mConfig.cancelText
        binding.btnConfirm.setTextColor(
            mConfig.confirmTextColor ?: ContextCompat.getColor(
                requireActivity(),
                R.color.dialog_text_color
            )
        )
        binding.btnCancel.setTextColor(
            mConfig.confirmTextColor ?: ContextCompat.getColor(
                requireActivity(),
                R.color.dialog_sub_text_color
            )
        )
        binding.btnConfirm.asBRRadii()
        binding.btnCancel.asBLRadii()
        if (binding.btnCancel.isGone || binding.btnConfirm.isGone) {
            binding.btnCancel.asBottomRadii()
            binding.btnConfirm.asBottomRadii()
        }
    }

}