package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.netRequest

sealed class TreeResult

class TreeResultError(val error: Throwable) : TreeResult()
class TreeResultSuccess(val data: MutableList<Tree>?) : TreeResult()

class TreeViewModel : ViewModel() {

    private val treeLiveData = MutableLiveData<TreeResult>()

    fun treeResult(): LiveData<TreeResult> {
        return treeLiveData
    }

    fun tree() {
        netRequest {
            request { API_SERVICE.tree() }
            success { treeLiveData.value = TreeResultSuccess(it) }
            error { treeLiveData.value = TreeResultError(it) }
        }
    }

}