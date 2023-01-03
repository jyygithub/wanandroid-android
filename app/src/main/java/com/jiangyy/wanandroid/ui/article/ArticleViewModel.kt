package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.ResultMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest

class ArticleViewModel : ViewModel() {

    private val _opResult = ResultMutableLiveData<Boolean>()

    val opResult get() = _opResult

    fun collect(id: String) {
        flowRequest<Any> {
            request { API_SERVICE.collect(id) }
            response {
                if (it.isSuccess) _opResult.value = Result.success(true)
                else _opResult.value = Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

    fun uncollect(id: String) {
        flowRequest<Any> {
            request { API_SERVICE.uncollect(id) }
            response {
                if (it.isSuccess) _opResult.value = Result.success(false)
                else _opResult.value = Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

}