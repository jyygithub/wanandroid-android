package com.jiangyy.common.statusview

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.jiangyy.common.databinding.SvEmptyLayoutBinding
import com.jiangyy.common.databinding.SvErrorLayoutBinding
import com.jiangyy.common.databinding.SvLoadingLayoutBinding

class StatusLayout private constructor(private val contentLayout: View) {

    private lateinit var loadingBinding: SvLoadingLayoutBinding
    private lateinit var emptyBinding: SvEmptyLayoutBinding
    private lateinit var errorBinding: SvErrorLayoutBinding

    var loadingText: CharSequence = "正在加载..."
    var emptyText: CharSequence = "数据为空"
    var errorText: CharSequence? = "数据加载失败"

    private var statusClickListener: OnStatusRetryListener? = null

    private var inflater: LayoutInflater? = null
    private var statusLayoutHelper: StatusLayoutHelper? = null

    private fun <VB : ViewBinding> inflater(inf: (LayoutInflater) -> VB): VB {
        if (null == inflater) {
            inflater = LayoutInflater.from(contentLayout.context)
        }
        return inf.invoke(inflater!!)
    }

    init {
        this.statusLayoutHelper = StatusLayoutHelper(contentLayout)
    }

    fun setOnStatusClickListener(listener: OnStatusRetryListener) {
        this.statusClickListener = listener
    }

    /**
     * 显示内容布局
     */
    fun showContentLayout() {
        statusLayoutHelper?.setContentLayout()
    }

    /**
     * 显示加载布局
     */
    fun showLoadingLayout(message: CharSequence? = null) {
        if (!::loadingBinding.isInitialized) {
            loadingBinding = inflater(SvLoadingLayoutBinding::inflate)
        }
        loadingBinding.tvStatusLoading.text = message ?: loadingText
        statusLayoutHelper?.showStatusLayout(loadingBinding.root)
    }

    /**
     * 显示空布局
     */
    fun showEmptyLayout(message: CharSequence?) {
        if (!::emptyBinding.isInitialized) {
            emptyBinding = inflater(SvEmptyLayoutBinding::inflate)
            emptyBinding.tvClickEmpty.setOnClickListener { statusClickListener!!.retryWhenEmpty(it) }
        }
        emptyBinding.tvStatusEmpty.text = message ?: emptyText
        statusLayoutHelper?.showStatusLayout(emptyBinding.root)
    }

    /**
     * 显示错误布局
     */
    fun showErrorLayout(message: CharSequence?) {
        if (!::errorBinding.isInitialized) {
            errorBinding = inflater(SvErrorLayoutBinding::inflate)
            errorBinding.tvClickError.setOnClickListener { statusClickListener!!.retryWhenError(it) }
        }
        errorBinding.tvStatusError.text = message ?: errorText
        statusLayoutHelper?.showStatusLayout(errorBinding.root)
    }

    companion object {
        fun newInstance(contentLayout: View) = StatusLayout(contentLayout)
    }

}