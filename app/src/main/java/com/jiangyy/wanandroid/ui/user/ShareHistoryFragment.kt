package com.jiangyy.wanandroid.ui.user

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.dialog.StringBottomListDialog
import com.jiangyy.wanandroid.ui.BaseArticlesFragment
import kotlinx.coroutines.launch

class ShareHistoryFragment private constructor() : BaseArticlesFragment() {

    private val mViewModel by viewModels<ShareHistoryViewModel>()

    private var mCurrentPosition = 0

    override fun initObserver() {

        lifecycleScope.launch {
            mViewModel.listShareHistory().collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
        mViewModel.unshare.observe(this) {
            if (it.isSuccess) {
                doneToast("取消分享成功")
                mAdapter.removeAt(mCurrentPosition)
            } else {
                errorToast(it.exceptionOrNull()?.message.orEmpty())
            }
        }
    }

    override fun initWidget() {
        mAdapter.setOnItemLongClickListener {  position ->
            mCurrentPosition = position
            StringBottomListDialog()
                .bindConfig { title = "文章操作" }
                .items("取消分享") { _, _ ->
                    val article = mAdapter.peek(position)
                    mViewModel.unshare(article?.id.orEmpty())
                }
                .show(childFragmentManager)

            false
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = ShareHistoryFragment()
    }

}