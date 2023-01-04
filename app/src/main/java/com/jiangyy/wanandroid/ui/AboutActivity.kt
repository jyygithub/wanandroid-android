package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import com.jiangyy.common.view.BaseActivity
import com.jiangyy.core.appVersionName
import com.jiangyy.wanandroid.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate) {

    override fun initWidget() {
        super.initWidget()
        binding.tvAppVersion.text = "Version $appVersionName"
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, AboutActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}