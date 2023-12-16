package com.jiangyy.wanandroid.kit

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun Context.toast(@StringRes res: Int): Toast = Toast
    .makeText(this.applicationContext, "", Toast.LENGTH_SHORT)
    .apply {
        setText(res)
        show()
    }

inline fun Context.toast(message: CharSequence): Toast = Toast
    .makeText(this.applicationContext, "", Toast.LENGTH_SHORT)
    .apply {
        setText(message)
        show()
    }


inline fun Context.longToast(@StringRes res: Int): Toast = Toast
    .makeText(this.applicationContext, "", Toast.LENGTH_LONG)
    .apply {
        setText(res)
        show()
    }

inline fun Context.longToast(message: CharSequence): Toast = Toast
    .makeText(this.applicationContext, "", Toast.LENGTH_LONG)
    .apply {
        setText(message)
        show()
    }

inline fun Fragment.toast(@StringRes res: Int) = requireActivity().toast(res)
inline fun Fragment.toast(message: CharSequence) = requireActivity().toast(message)
inline fun Fragment.longToast(@StringRes res: Int) = requireActivity().longToast(res)
inline fun Fragment.longToast(message: CharSequence) = requireActivity().longToast(message)