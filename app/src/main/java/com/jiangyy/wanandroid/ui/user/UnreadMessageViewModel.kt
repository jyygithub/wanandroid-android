package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Message
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.netRequest

class UnreadMessageViewModel : ViewModel() {

    private val _messages = MutableLiveData<Result<PageData<Message>?>>()

    val messages get() = _messages

    fun listMessage() {
        netRequest {
            request { API_SERVICE.listUnreadMessage(1) }
            success { _messages.value = Result.success(it) }
            error { _messages.value = Result.failure(it) }
        }
    }

}