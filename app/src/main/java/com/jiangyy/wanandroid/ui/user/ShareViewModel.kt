package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.netRequest

class ShareViewModel : ViewModel() {

    private val _share = MutableLiveData<Result<Any?>>()

    val share get() = _share

    fun share(title: String, link: String) {
        netRequest {
            request { API_SERVICE.share(title, link) }
            success { _share.value = Result.success(it) }
            error { _share.value = Result.failure(it) }
        }
    }

}