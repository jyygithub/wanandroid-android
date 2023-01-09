package com.jiangyy.dialog.utils

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.widget.TextView
import com.jiangyy.dialog.R

//左上[X_Radius,Y_Radius],右上[X_Radius,Y_Radius],右下[X_Radius,Y_Radius],左下[X_Radius,Y_Radius]

private fun View.cornerDp(): Float = this.resources.getDimensionPixelOffset(R.dimen.dialog_radius).toFloat()

private fun View.asRadii(value: FloatArray) {
    this.setBackgroundResource(R.drawable.dialog_selector_button)
    val temp = (this.background.mutate() as RippleDrawable)
    (temp.getDrawable(0).mutate() as GradientDrawable).cornerRadii = value
    (temp.getDrawable(1).mutate() as GradientDrawable).cornerRadii = value
    this.background = temp
}

fun View.asBLRadii() = this.asRadii(floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, cornerDp(), cornerDp()))
fun View.asBRRadii() = this.asRadii(floatArrayOf(0f, 0f, 0f, 0f, cornerDp(), cornerDp(), 0f, 0f))
fun View.asTopRadii() = this.asRadii(floatArrayOf(cornerDp(), cornerDp(), cornerDp(), cornerDp(), 0f, 0f, 0f, 0f))
fun View.asBottomRadii() = this.asRadii(floatArrayOf(0f, 0f, 0f, 0f, cornerDp(), cornerDp(), cornerDp(), cornerDp()))
fun View.asCenterRadii() = this.asRadii(floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f))

var View.isGone: Boolean
    get() = this.visibility == View.GONE
    set(value) {
        if (value) {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
        }
    }