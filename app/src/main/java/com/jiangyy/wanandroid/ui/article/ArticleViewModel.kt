package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.netRequest

class ArticleViewModel : ViewModel() {

    private val _opResult = MutableLiveData<Result<Boolean>>()

    val opResult get() = _opResult

    fun collect(id: String) {
        netRequest {
            request { API_SERVICE.collect(id) }
            success { _opResult.value = Result.success(true) }
            error { _opResult.value = Result.failure(it) }
        }
    }

    fun uncollect(id: String) {
        netRequest {
            request { API_SERVICE.uncollect(id) }
            success { _opResult.value = Result.success(false) }
            error { _opResult.value = Result.failure(it) }
        }
    }

}