package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Message
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.netRequest

class ReadedMessageViewModel : ViewModel() {

    private val refreshLiveData = MutableLiveData<PageData<Message>>()
    private val loadMoreLiveData = MutableLiveData<PageData<Message>>()
    private val errorLiveData = MutableLiveData<Pair<Throwable, Boolean>>()

    var mPage = 1

    fun firstData(): LiveData<PageData<Message>> {
        return refreshLiveData
    }

    fun loadMoreData(): LiveData<PageData<Message>> {
        return loadMoreLiveData
    }

    fun dataError(): LiveData<Pair<Throwable, Boolean>> {
        return errorLiveData
    }

    fun firstLoad() {
        mPage = 1
        netRequest {
            request { API_SERVICE.listReadedMessage(mPage) }
            success { refreshLiveData.value = it }
            error { errorLiveData.value = it to false }
        }
    }

    fun loadMore() {
        netRequest {
            request { API_SERVICE.listReadedMessage(mPage) }
            success { loadMoreLiveData.value = it }
            error { errorLiveData.value = it to true }
        }
    }

}