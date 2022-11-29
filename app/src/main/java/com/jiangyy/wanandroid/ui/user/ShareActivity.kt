package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.core.warnToast
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityShareBinding
import com.jiangyy.wanandroid.logic.ArticleUrl
import kotlinx.coroutines.launch
import rxhttp.awaitResult

class ShareActivity : BaseActivity<ActivityShareBinding>() {

    override fun initValue() {

    }

    override fun initWidget() {

        binding.toolbar.setOnEndListener {
            collect()
        }

    }

    private fun collect() {

        val title = binding.etTitle.text.toString().trim()
        val link = binding.etLink.text.toString().trim()

        if (title.isBlank() || link.isBlank()) {
            warnToast("请输入内容")
            return
        }

        lifecycleScope.launch {
            ArticleUrl.share(title, link)
                .awaitResult {
                    if (it.isSuccess()) {
                        doneToast("分享成功")
                        finish()
                    } else {
                        errorToast(it.errorMsg.orEmpty())
                    }
                }
                .onFailure {
                    errorToast("分享失败")
                }
        }

    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, ShareActivity::class.java))
        }
    }

}