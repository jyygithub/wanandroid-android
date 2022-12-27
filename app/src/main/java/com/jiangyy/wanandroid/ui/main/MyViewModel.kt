package com.jiangyy.wanandroid.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.core.orZero
import com.jiangyy.wanandroid.entity.UserInfo
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.netRequest
import com.jiangyy.wanandroid.utils.DataStoreUtils

class MyViewModel : ViewModel() {

    private val logoutLiveData = MutableLiveData<Boolean>()

    fun logout() {
        netRequest {
            request { API_SERVICE.logout() }
            success {
                DataStoreUtils.logout()
                loginOrOut(false)
            }
            error { }
        }
    }

    private val userInfoLiveData = MutableLiveData<Pair<Long, Int>>()

    fun userInfo(): LiveData<Pair<Long, Int>> {
        return userInfoLiveData
    }

    fun infoUser() {
        if (!DataStoreUtils.logged) return
        netRequest<UserInfo> {
            request { API_SERVICE.infoUser() }
            success { userInfoLiveData.value = it?.userInfo?.coinCount.orZero() to it?.userInfo?.collectIds?.size.orZero() }
            error { }
        }
    }

    private val messageCountLiveData = MutableLiveData<Int>()
    private val messageCountErrorLiveData = MutableLiveData<Throwable>()

    fun messageCount(): Pair<LiveData<Int>, LiveData<Throwable>> {
        return messageCountLiveData to messageCountErrorLiveData
    }

    fun getMessageCount() {
        if (!DataStoreUtils.logged) return
        netRequest {
            request { API_SERVICE.getUnreadMessageCount() }
            success { messageCountLiveData.value = it }
            error { messageCountErrorLiveData.value = it }
        }
    }

    private val loggedLiveDate = MutableLiveData<Boolean>()

    fun loginOrOut(status: Boolean) {
        loggedLiveDate.postValue(status)
    }

    fun loggerStatus(): LiveData<Boolean> {
        return loggedLiveDate
    }

}