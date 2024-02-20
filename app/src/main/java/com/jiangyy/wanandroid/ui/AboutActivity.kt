package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import com.jiangyy.wanandroid.databinding.ActivityAboutBinding
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.core.appVersion

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.tvAppVersion.text = "Version ${appVersion.versionName}"
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, AboutActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}