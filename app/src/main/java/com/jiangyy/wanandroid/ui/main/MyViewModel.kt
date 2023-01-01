package com.jiangyy.wanandroid.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.UserInfo
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.ResultMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest
import com.jiangyy.wanandroid.utils.DataStoreUtils

class MyViewModel : ViewModel() {

    private val loggedLiveDate = MutableLiveData<Boolean>()

    fun loginOrOut(status: Boolean) {
        loggedLiveDate.postValue(status)
    }

    fun loggerStatus(): LiveData<Boolean> {
        return loggedLiveDate
    }

    fun logout() {
        flowRequest {
            request { API_SERVICE.logout() }
            response {
                if (it.isSuccess) {
                    DataStoreUtils.logout()
                    loginOrOut(false)
                }
            }
        }
    }

    private val userInfoLiveData = ResultMutableLiveData<UserInfo>()

    val userInfo get() = userInfoLiveData

    fun infoUser() {
        if (!DataStoreUtils.logged) return
        flowRequest {
            request { API_SERVICE.infoUser() }
            response { userInfo.value = it }
        }
    }

    private val _messageCountLiveData = ResultMutableLiveData<Int>()

    val messageCount get() = _messageCountLiveData

    fun getMessageCount() {
        if (!DataStoreUtils.logged) return
        flowRequest {
            request { API_SERVICE.getUnreadMessageCount() }
            response { _messageCountLiveData.value = it }
        }
    }

}