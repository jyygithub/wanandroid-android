package com.jiangyy.wanandroid.ui

import android.os.Bundle
import com.koonny.appcompat.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityLauncherBinding

class LauncherActivity : BaseActivity<ActivityLauncherBinding>(ActivityLauncherBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.actionStart(this)
        finish()
    }

}