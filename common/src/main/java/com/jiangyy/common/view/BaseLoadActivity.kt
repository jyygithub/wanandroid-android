package com.jiangyy.common.view

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.jiangyy.common.statusview.OnStatusRetryListener
import com.jiangyy.common.statusview.StatusLayout
import com.jiangyy.common.statusview.StatusModule

abstract class BaseLoadActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : BaseActivity<VB>(inflate),
    OnStatusRetryListener, StatusModule {

    private lateinit var mStatusLayout: StatusLayout

    override fun initWidget() {
        super.initWidget()
        if (!::mStatusLayout.isInitialized) {
            mStatusLayout = StatusLayout.newInstance(viewBindStatus)
                .apply {
                    setOnStatusClickListener(this@BaseLoadActivity)
                    loadingText = "玩玩努力中...."
                    emptyText = "暂无数据"
                    errorText = "数据加载失败"
                }
        }
    }

    override fun preLoad() {
        super.preLoad()
        mStatusLayout.showLoadingLayout()
    }

    protected open fun preLoadSuccess() {
        mStatusLayout.showContentLayout()
    }

    private var retryWhenError: (() -> Unit)? = null
    private var retryWhenEmpty: (() -> Unit)? = null

    protected fun preLoadEmpty(message: CharSequence? = null, block: (() -> Unit)? = null) {
        mStatusLayout.showEmptyLayout(message)
        retryWhenEmpty = block
    }

    protected fun preLoadError(message: CharSequence? = null, block: (() -> Unit)? = null) {
        mStatusLayout.showErrorLayout(message)
        retryWhenError = block
    }

    override fun retryWhenEmpty(view: View) {
        retryWhenEmpty?.invoke() ?: preLoad()
    }

    override fun retryWhenError(view: View) {
        retryWhenError?.invoke() ?: preLoad()
    }

}