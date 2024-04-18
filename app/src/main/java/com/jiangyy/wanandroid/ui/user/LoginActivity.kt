package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityLoginBinding
import com.jiangyy.wanandroid.kit.click
import com.jiangyy.wanandroid.ktor.UserApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.btnLogin.click {
            login()
        }
    }

    private fun login() {
        lifecycleScope.launch {
            flow {
                emit(UserApi().login("", ""))
            }.catch { }.collect {

            }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

}