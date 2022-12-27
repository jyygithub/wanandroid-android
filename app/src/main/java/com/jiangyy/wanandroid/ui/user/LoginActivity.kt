package com.jiangyy.wanandroid.ui.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityLoginBinding
import com.jiangyy.wanandroid.utils.DataStoreUtils

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val mViewModel by viewModels<LoginViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.btnLogin.setOnClickListener {
            mViewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
        mViewModel.loginResult().observe(this) {
            doneToast("登录成功")
            DataStoreUtils.logged = true
            DataStoreUtils.updateUser(it)
            setResult(Activity.RESULT_OK)
            finish()
        }
        mViewModel.loginError().observe(this) {
            errorToast(it.message.orEmpty())
        }
    }

    companion object {
        fun actionIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

}