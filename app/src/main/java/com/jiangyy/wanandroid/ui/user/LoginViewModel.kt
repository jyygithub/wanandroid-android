package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.User
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.ResultMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest

class LoginViewModel : ViewModel() {

    private val _loginLiveData = ResultMutableLiveData<User>()

    val loginResult get() = _loginLiveData

    fun login(username: String, password: String) {
        flowRequest {
            request { API_SERVICE.login(username, password) }
            response { _loginLiveData.value = it }
        }
    }

}