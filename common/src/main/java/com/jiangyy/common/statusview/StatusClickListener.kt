package com.jiangyy.common.statusview

import android.view.View

interface OnStatusRetryListener {

    /**
     * 空数据布局点击方法
     * @param view 被点击的view
     */
    fun retryWhenEmpty(view: View)

    /**
     * 错误数据布局点击方法
     * @param view 被点击的view
     */
    fun retryWhenError(view: View)

}