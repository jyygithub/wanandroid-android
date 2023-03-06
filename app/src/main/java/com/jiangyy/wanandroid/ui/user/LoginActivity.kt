package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.core.toast
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.ActivityLoginBinding
import com.jiangyy.wanandroid.entity.User
import com.jiangyy.wanandroid.ui.main.CurrentUser
import com.jiangyy.wanandroid.ui.main.dataStore
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnLogin.setOnClickListener {
            flowRequest<User> {
                request {
                    RetrofitHelper.getInstance().create(Api::class.java).login(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                }
                response {
                    if (it.isSuccess) {
                        loginSuccess(CurrentUser(it.getOrNull()?.username, it.getOrNull()?.nickname))
                    } else {
                        toast(it.exceptionOrNull()?.message.orEmpty())
                    }
                }
            }
        }

    }

    private fun loginSuccess(currentUser: CurrentUser) {
        lifecycleScope.launch {
            dataStore.edit {
                it[stringPreferencesKey("currentUser")] = Gson().toJson(currentUser)
            }
            finish()
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

}