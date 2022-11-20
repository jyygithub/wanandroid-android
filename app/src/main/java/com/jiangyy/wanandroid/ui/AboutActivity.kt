package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import com.jiangyy.core.appVersionName
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity<ActivityAboutBinding>() {

    override fun initValue() {

    }

    override fun initWidget() {
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