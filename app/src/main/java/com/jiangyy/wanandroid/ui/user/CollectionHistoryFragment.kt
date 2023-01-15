package com.jiangyy.wanandroid.ui.user

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jiangyy.common.utils.doneToast
import com.jiangyy.common.utils.errorToast
import com.jiangyy.dialog.ConfirmDialog
import com.jiangyy.wanandroid.logic.isSuccessOrNull
import com.jiangyy.wanandroid.ui.BaseArticlesFragment
import com.jiangyy.wanandroid.ui.main.ArticlesViewModel
import kotlinx.coroutines.launch

class CollectionHistoryFragment private constructor() : BaseArticlesFragment() {

    private val viewModel by viewModels<ArticlesViewModel>()

    private var mCurrentPosition = 0

    override fun initWidget() {
        super.initWidget()
        mAdapter.itemLongClick {
            mCurrentPosition = it
            ConfirmDialog()
                .bindConfig { title = "取消收藏？" }
                .confirm { _ ->
                    viewModel.uncollect(mAdapter.peek(it)?.id.orEmpty())
                }
                .show(childFragmentManager)
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.uncollect.observe(this) {
            if (it.isSuccessOrNull) {
                doneToast("操作成功")
                mAdapter.removeAt(mCurrentPosition)
            } else {
                errorToast(it.exceptionOrNull()?.message.orEmpty())
            }
        }
        lifecycleScope.launch {
            viewModel.listCollect().collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CollectionHistoryFragment()
    }

}