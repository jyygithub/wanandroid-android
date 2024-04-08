package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

}