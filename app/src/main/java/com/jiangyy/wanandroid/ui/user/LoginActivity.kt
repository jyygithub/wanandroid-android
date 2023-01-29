package com.jiangyy.wanandroid.ui.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jiangyy.common.utils.doneToast
import com.jiangyy.common.utils.errorToast
import com.jiangyy.common.view.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityLoginBinding
import com.jiangyy.wanandroid.utils.DataStoreUtils

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    private val mViewModel by viewModels<LoginViewModel>()

    override fun initWidget() {
        super.initWidget()
        binding.btnLogin.setOnClickListener {
            mViewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    override fun initObserver() {
        super.initObserver()
        mViewModel.loginResult.observe(this) {
            if (it.isSuccess) {
                doneToast("登录成功")
                DataStoreUtils.logged = true
                DataStoreUtils.updateUser(it.getOrNull())
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                errorToast(it.exceptionOrNull()?.message.orEmpty())
            }
        }
    }

    companion object {
        fun actionIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

}