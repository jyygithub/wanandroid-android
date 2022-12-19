package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.netRequest

class SubListViewModel : ViewModel() {

    private val _sublist = MutableLiveData<Result<MutableList<Tree>?>>()

    val sublist get() = _sublist

    fun listSub() {
        netRequest {
            request { API_SERVICE.listSub() }
            success { _sublist.value = Result.success(it) }
            error { _sublist.value = Result.failure(it) }
        }
    }

}