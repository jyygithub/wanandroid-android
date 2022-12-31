package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.ResultMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest

class SubListViewModel : ViewModel() {

    private val _sublist = ResultMutableLiveData<MutableList<Tree>>()

    val sublist get() = _sublist

    fun listSub() {
        flowRequest {
            request { API_SERVICE.listSub() }
            response { _sublist.value = it }
        }
    }

}