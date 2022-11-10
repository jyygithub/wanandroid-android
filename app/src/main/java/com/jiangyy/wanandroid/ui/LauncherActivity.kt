package com.jiangyy.wanandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jiangyy.wanandroid.R

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        MainActivity.actionStart(this)
        finish()
    }

}