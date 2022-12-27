package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.User
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.netRequest

class LoginViewModel : ViewModel() {

    private val loginLiveData = MutableLiveData<User>()
    private val loginErrorLiveData = MutableLiveData<Throwable>()

    fun loginResult(): LiveData<User> {
        return loginLiveData
    }

    fun loginError(): LiveData<Throwable> {
        return loginErrorLiveData
    }

    fun login(username: String, password: String) {
        netRequest {
            request { API_SERVICE.login(username, password) }
            success { loginLiveData.value = it }
            error { loginErrorLiveData.value = it }
        }
    }

}