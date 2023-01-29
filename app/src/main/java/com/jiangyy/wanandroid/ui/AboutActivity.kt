package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import com.jiangyy.common.view.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityAboutBinding
import com.jiangyy.wanandroid.utils.appVersionInfo

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate) {

    override fun initWidget() {
        super.initWidget()
        binding.tvAppVersion.text = "Version ${appVersionInfo.name}"
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, AboutActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}