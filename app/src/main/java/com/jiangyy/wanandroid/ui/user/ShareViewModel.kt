package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.ResultMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest

class ShareViewModel : ViewModel() {

    private val _share = ResultMutableLiveData<Any>()

    val share get() = _share

    fun share(title: String, link: String) {
        flowRequest {
            request { API_SERVICE.share(title, link) }
            response { _share.value = it }
        }
    }

}