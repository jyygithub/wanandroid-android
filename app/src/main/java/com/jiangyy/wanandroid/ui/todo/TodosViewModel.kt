package com.jiangyy.wanandroid.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Todo
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.netRequest

class TodosViewModel : ViewModel() {

    private val refreshLiveData = MutableLiveData<PageData<Todo>>()
    private val loadMoreLiveData = MutableLiveData<PageData<Todo>>()
    private val errorLiveData = MutableLiveData<Pair<Throwable, Boolean>>()

    var mPage = 0
    private var mStatus = 0

    fun fetchStatus(status: Int) {
        mStatus = status
        firstLoad()
    }

    fun firstData(): LiveData<PageData<Todo>> {
        return refreshLiveData
    }

    fun loadMoreData(): LiveData<PageData<Todo>> {
        return loadMoreLiveData
    }

    fun dataError(): LiveData<Pair<Throwable, Boolean>> {
        return errorLiveData
    }

    fun firstLoad() {
        mPage = 0
        netRequest {
            request { API_SERVICE.pageTodo(mPage, mStatus) }
            success { refreshLiveData.value = it }
            error { errorLiveData.value = it to false }
        }
    }

    fun loadMore() {
        netRequest {
            request { API_SERVICE.pageTodo(mPage, mStatus) }
            success { loadMoreLiveData.value = it }
            error { errorLiveData.value = it to true }
        }
    }

}