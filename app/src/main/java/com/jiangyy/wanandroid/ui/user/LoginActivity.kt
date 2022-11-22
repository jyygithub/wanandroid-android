package com.jiangyy.wanandroid.ui.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityLoginBinding
import com.jiangyy.wanandroid.logic.UserUrl
import com.jiangyy.wanandroid.utils.DataStoreUtils
import kotlinx.coroutines.launch
import rxhttp.awaitResult

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun initValue() {

    }

    override fun initWidget() {
        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        lifecycleScope.launch {
            UserUrl.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
                .awaitResult {
                    if (it.isSuccess()) {
                        doneToast("登录成功")
                        DataStoreUtils.logged = true
                        DataStoreUtils.updateUser(it.data)
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        errorToast(it.errorMsg.orEmpty())
                    }
                }
                .onFailure {

                }
        }
    }

    companion object {
        fun actionIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

}