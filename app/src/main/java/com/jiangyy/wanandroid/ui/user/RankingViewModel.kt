package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Coin
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.netRequest

class RankingViewModel : ViewModel() {

    private val refreshLiveData = MutableLiveData<PageData<Coin>>()
    private val loadMoreLiveData = MutableLiveData<PageData<Coin>>()
    private val errorLiveData = MutableLiveData<Pair<Throwable, Boolean>>()

    var mPage = 1

    fun firstData(): LiveData<PageData<Coin>> {
        return refreshLiveData
    }

    fun loadMoreData(): LiveData<PageData<Coin>> {
        return loadMoreLiveData
    }

    fun dataError(): LiveData<Pair<Throwable, Boolean>> {
        return errorLiveData
    }

    fun firstLoad() {
        mPage = 0
        netRequest {
            request { API_SERVICE.ranking(mPage) }
            success { refreshLiveData.value = it }
            error { errorLiveData.value = it to false }
        }
    }

    fun loadMore() {
        netRequest {
            request { API_SERVICE.ranking(mPage) }
            success { loadMoreLiveData.value = it }
            error { errorLiveData.value = it to true }
        }
    }

}