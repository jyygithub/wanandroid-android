package com.jiangyy.common.utils

import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.jiangyy.common.R
import com.jiangyy.app.BaseActivity
import com.jiangyy.common.view.BaseFragment

private fun BaseActivity<out ViewBinding>.createSnackbar(text: CharSequence): Snackbar {
    return Snackbar.make(this.binding.root, text, Snackbar.LENGTH_SHORT).apply {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER
        view.layoutParams = params
    }
}

private fun BaseActivity<out ViewBinding>.statusSnackbar(text: CharSequence, @DrawableRes icon: Int) {
    createSnackbar(text).apply {
        val drawable = ContextCompat.getDrawable(this@statusSnackbar, icon)
        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            drawable?.setTint(currentTextColor)
            compoundDrawablePadding = 20
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
        }
    }.show()
}

fun BaseActivity<out ViewBinding>.toast(text: CharSequence) = createSnackbar(text).show()
fun BaseActivity<out ViewBinding>.doneToast(text: CharSequence) = statusSnackbar(text, R.drawable.ic_snackbar_done)
fun BaseActivity<out ViewBinding>.warnToast(text: CharSequence) = statusSnackbar(text, R.drawable.ic_snackbar_warn)
fun BaseActivity<out ViewBinding>.errorToast(text: CharSequence) = statusSnackbar(text, R.drawable.ic_snackbar_error)

private fun BaseFragment<out ViewBinding>.createSnackbar(text: CharSequence): Snackbar {
    return Snackbar.make(this.binding.root, text, Snackbar.LENGTH_SHORT).apply {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER
        view.layoutParams = params
    }
}

private fun BaseFragment<out ViewBinding>.statusSnackbar(text: CharSequence, @DrawableRes icon: Int) {
    createSnackbar(text).apply {
        val drawable = ContextCompat.getDrawable(context, icon)
        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            drawable?.setTint(currentTextColor)
            compoundDrawablePadding = 20
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
        }
    }.show()
}

fun BaseFragment<out ViewBinding>.toast(text: CharSequence) = createSnackbar(text).show()
fun BaseFragment<out ViewBinding>.doneToast(text: CharSequence) = statusSnackbar(text, R.drawable.ic_snackbar_done)
fun BaseFragment<out ViewBinding>.warnToast(text: CharSequence) = statusSnackbar(text, R.drawable.ic_snackbar_warn)
fun BaseFragment<out ViewBinding>.errorToast(text: CharSequence) = statusSnackbar(text, R.drawable.ic_snackbar_error)