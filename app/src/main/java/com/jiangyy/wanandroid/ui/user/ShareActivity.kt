package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.core.warnToast
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityShareBinding

class ShareActivity : BaseActivity<ActivityShareBinding>() {

    private val mViewModel by viewModels<ShareViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {

        binding.toolbar.setOnEndListener {
            collect()
        }
        mViewModel.share.observe(this) {
            if (it.isSuccess) {
                doneToast("分享成功")
                finish()
            } else {
                errorToast(it.exceptionOrNull()?.message.orEmpty())
            }
        }
    }

    private fun collect() {

        val title = binding.etTitle.text.toString().trim()
        val link = binding.etLink.text.toString().trim()

        if (title.isBlank() || link.isBlank()) {
            warnToast("请输入内容")
            return
        }
        mViewModel.share(title, link)

    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, ShareActivity::class.java))
        }
    }

}