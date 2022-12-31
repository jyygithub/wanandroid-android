package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.ResultMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest

class TreeViewModel : ViewModel() {

    private val _treeLiveData = ResultMutableLiveData<MutableList<Tree>>()

    val treeResult get() = _treeLiveData

    fun tree() {
        flowRequest {
            request { API_SERVICE.tree() }
            response { _treeLiveData.value = it }
        }
    }

}