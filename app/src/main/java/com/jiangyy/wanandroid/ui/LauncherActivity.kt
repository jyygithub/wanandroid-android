package com.jiangyy.wanandroid.ui

import com.jiangyy.common.view.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityLauncherBinding

class LauncherActivity : BaseActivity<ActivityLauncherBinding>(ActivityLauncherBinding::inflate) {

    override fun initWidget() {
        super.initWidget()
        MainActivity.actionStart(this)
        finish()
    }

}