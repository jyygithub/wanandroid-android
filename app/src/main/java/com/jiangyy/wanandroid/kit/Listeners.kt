package com.jiangyy.wanandroid.kit

abstract class FastClickListener(private var millisecond: Long) : android.view.View.OnClickListener {
    private var mLastClickTime: Long = 0
    override fun onClick(v: android.view.View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - mLastClickTime > millisecond) {
            v.hideSoftInput()
            onClick()
            mLastClickTime = System.currentTimeMillis()
        }
    }

    protected abstract fun onClick()
}

/**
 * @param millisecond Long 两次点击间隔最短时间(毫秒，默认：3000)
 */
inline fun android.view.View.click(millisecond: Long = 3000L, crossinline listener: () -> Unit) {
    this.setOnClickListener(object : FastClickListener(millisecond) {
        override fun onClick() {
            listener.invoke()
        }
    })
}