package com.jiangyy.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.jiangyy.common.statusview.OnStatusRetryListener
import com.jiangyy.common.statusview.StatusLayout
import com.jiangyy.common.statusview.StatusModule

abstract class BaseLoadFragment<VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    BaseFragment<VB>(inflate), OnStatusRetryListener, StatusModule {

    private lateinit var mStatusLayout: StatusLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate(inflater, container, false)
        var rootView = binding.root
        if (!::mStatusLayout.isInitialized) {
            if (viewBindStatus.parent == null) {
                val parentView = FrameLayout(viewBindStatus.context)
                parentView.layoutParams =
                    FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                parentView.addView(binding.root)
                rootView = parentView
            }
            mStatusLayout = StatusLayout.newInstance(viewBindStatus)
                .apply {
                    setOnStatusClickListener(this@BaseLoadFragment)
                    loadingText = "玩玩努力中...."
                    emptyText = "暂无数据"
                    errorText = "数据加载失败"
                }
        }
        return rootView
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