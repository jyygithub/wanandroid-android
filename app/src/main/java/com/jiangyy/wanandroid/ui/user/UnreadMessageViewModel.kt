package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Message
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.ResultMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest

class UnreadMessageViewModel : ViewModel() {

    private val _messages = ResultMutableLiveData<PageData<Message>>()

    val messages get() = _messages

    fun listMessage() {
        flowRequest {
            request { API_SERVICE.listUnreadMessage(1) }
            response { _messages.value = it }
        }
    }

}