package com.jiangyy.widget

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

class Toolbar : ViewGroup {

    private var mTitleTextView: TextView? = null
    private var mTitleGravity: Int? = null
    private var mTitleTextColor: ColorStateList? = null

    private var mStartTextView: TextView? = null
    private var mStartImageView: ImageView? = null
    private var mStartTintColor: ColorStateList? = null

    private var mEndTextView: TextView? = null
    private var mEndImageView: ImageView? = null
    private var mEndTintColor: ColorStateList? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, androidx.appcompat.R.attr.toolbarStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0)
        mTitleGravity = a.getInteger(R.styleable.Toolbar_titleGravity, Gravity.CENTER)
        mTitleTextColor = a.getColorStateList(R.styleable.Toolbar_titleTextColor)
        mStartTintColor = if (a.hasValue(R.styleable.Toolbar_startTint)) {
            a.getColorStateList(R.styleable.Toolbar_startTint)
        } else {
            mTitleTextColor
        }
        mEndTintColor = if (a.hasValue(R.styleable.Toolbar_endTint)) {
            a.getColorStateList(R.styleable.Toolbar_endTint)
        } else {
            mTitleTextColor
        }

        setTitle(a.getText(R.styleable.Toolbar_title))
        setStart(a.getDrawable(R.styleable.Toolbar_startIcon), a.getText(R.styleable.Toolbar_startText))
        setEnd(a.getDrawable(R.styleable.Toolbar_endIcon), a.getText(R.styleable.Toolbar_endText))

        a.recycle()

    }

    fun setTitle(title: CharSequence?) {
        if (!title.isNullOrEmpty()) {
            if (mTitleTextView == null) {
                mTitleTextView = AppCompatTextView(context).apply {
                    setSingleLine()
                    maxEms = 10
                    ellipsize = TextUtils.TruncateAt.END
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    setTextColor(mTitleTextColor)
                    addView(this, generateDefaultLayoutParams())
                }
            }
        }
        mTitleTextView?.text = title
    }

    fun setStart(startIcon: Drawable?, startText: CharSequence?) {
        if (startIcon != null) {
            if (mStartImageView == null) {
                mStartImageView = AppCompatImageView(context).apply {
                    setBackgroundResource(R.drawable.jandroid_shape_press)
                    imageTintList = mStartTintColor
                    this.setOnClickListener {

                    }
                    addView(this, generateDefaultLayoutParams())
                }
            }
        }
        if (startText != null) {
            if (mStartTextView == null) {
                mStartTextView = AppCompatTextView(context).apply {
                    setBackgroundResource(R.drawable.jandroid_shape_press)
                    setSingleLine()
                    maxEms = 5
                    ellipsize = TextUtils.TruncateAt.END
                    setTextColor(mTitleTextColor)
                    addView(this, generateDefaultLayoutParams())
                }
            }
        }
        mStartImageView?.setImageDrawable(startIcon)
        mStartImageView?.setOnClickListener {
            (context as Activity?)?.onBackPressed()
        }
        mStartTextView?.text = startText
        mStartTextView?.setOnClickListener {
            (context as Activity?)?.onBackPressed()
        }
    }

    fun setEnd(endIcon: Drawable?, endText: CharSequence?) {
        if (endIcon != null) {
            if (mEndImageView == null) {
                mEndImageView = AppCompatImageView(context).apply {
                    setBackgroundResource(R.drawable.jandroid_shape_press)
                    imageTintList = mEndTintColor
                    this.setOnClickListener {

                    }
                    addView(this, generateDefaultLayoutParams())
                }
            }
        }
        if (endText != null) {
            if (mEndTextView == null) {
                mEndTextView = AppCompatTextView(context).apply {
                    setBackgroundResource(R.drawable.jandroid_shape_press)
                    setSingleLine()
                    maxEms = 5
                    ellipsize = TextUtils.TruncateAt.END
                    setTextColor(mTitleTextColor)
                    addView(this, generateDefaultLayoutParams())
                }
            }
        }
        mEndImageView?.setImageDrawable(endIcon)
        mEndTextView?.text = endText
    }

    fun setOnStartListener(listener: View.OnClickListener) {
        if (shouldLayout(mStartImageView)) {
            mStartImageView!!.setOnClickListener(listener)
        }
        if (shouldLayout(mStartTextView)) {
            mStartTextView!!.setOnClickListener(listener)
        }
    }

    fun setOnEndListener(listener: View.OnClickListener) {
        if (shouldLayout(mEndImageView)) {
            mEndImageView!!.setOnClickListener(listener)
        }
        if (shouldLayout(mEndTextView)) {
            mEndTextView!!.setOnClickListener(listener)
        }
    }

    private fun shouldLayout(view: View?): Boolean {
        return view != null && view.parent == this && view.visibility != GONE
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

        var start = 16f.dp()

        if (shouldLayout(mStartImageView)) {
            mStartImageView!!.layout(
                16f.dp(),
                (height - mStartImageView!!.measuredHeight) / 2,
                mStartImageView!!.measuredWidth + 16f.dp(),
                (height + mStartImageView!!.measuredHeight) / 2
            )
            start = maxOf(start, mStartImageView!!.measuredWidth + 24f.dp())
        }
        if (shouldLayout(mStartTextView)) {
            mStartTextView!!.layout(
                16f.dp(),
                (height - mStartTextView!!.measuredHeight) / 2,
                mStartTextView!!.measuredWidth + 16f.dp(),
                (height + mStartTextView!!.measuredHeight) / 2
            )
            start = maxOf(start, mStartTextView!!.measuredWidth + 24f.dp())
        }
        if (shouldLayout(mEndImageView)) {
            mEndImageView!!.layout(
                width - mEndImageView!!.measuredWidth - 16f.dp(),
                (height - mEndImageView!!.measuredHeight) / 2,
                width - 16f.dp(),
                (height + mEndImageView!!.measuredHeight) / 2
            )
        }
        if (shouldLayout(mEndTextView)) {
            mEndTextView!!.layout(
                width - mEndTextView!!.measuredWidth - 16f.dp(),
                (height - mEndTextView!!.measuredHeight) / 2,
                width - 16f.dp(),
                (height + mEndTextView!!.measuredHeight) / 2
            )
        }
        if (shouldLayout(mTitleTextView)) {
            if (mTitleGravity == Gravity.CENTER) {
                mTitleTextView!!.layout(
                    (width - mTitleTextView!!.measuredWidth) / 2,
                    (height - mTitleTextView!!.measuredHeight) / 2,
                    (width + mTitleTextView!!.measuredWidth) / 2,
                    (height + mTitleTextView!!.measuredHeight) / 2
                )
            } else {
                mTitleTextView!!.layout(
                    start,
                    (height - mTitleTextView!!.measuredHeight) / 2,
                    start + mTitleTextView!!.measuredWidth,
                    (height + mTitleTextView!!.measuredHeight) / 2
                )
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var childState = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
        }

        val measuredWidth = View.resolveSizeAndState(
            width.coerceAtLeast(suggestedMinimumWidth),
            widthMeasureSpec, childState and View.MEASURED_STATE_MASK
        )
        val measuredHeight = View.resolveSizeAndState(
            height.coerceAtLeast(suggestedMinimumHeight),
            heightMeasureSpec, childState shl MEASURED_HEIGHT_STATE_SHIFT
        )
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun getSuggestedMinimumHeight(): Int {
        return resources.getDimensionPixelSize(R.dimen.jandroid_actionBarSize)
    }

    private fun Float.dp(): Int = (resources.displayMetrics.density * this + 0.5f).toInt()

}