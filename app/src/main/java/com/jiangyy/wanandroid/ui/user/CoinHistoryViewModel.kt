package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.CoinHistory
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.netRequest

class CoinHistoryViewModel : ViewModel() {

    private val refreshLiveData = MutableLiveData<PageData<CoinHistory>>()
    private val loadMoreLiveData = MutableLiveData<PageData<CoinHistory>>()
    private val errorLiveData = MutableLiveData<Pair<Throwable, Boolean>>()

    var mPage = 1

    fun firstData(): LiveData<PageData<CoinHistory>> {
        return refreshLiveData
    }

    fun loadMoreData(): LiveData<PageData<CoinHistory>> {
        return loadMoreLiveData
    }

    fun dataError(): LiveData<Pair<Throwable, Boolean>> {
        return errorLiveData
    }

    fun firstLoad() {
        mPage = 1
        netRequest {
            request { API_SERVICE.pageCoinHistory(mPage) }
            success { refreshLiveData.value = it }
            error { errorLiveData.value = it to false }
        }
    }

    fun loadMore() {
        netRequest {
            request { API_SERVICE.pageCoinHistory(mPage) }
            success { loadMoreLiveData.value = it }
            error { errorLiveData.value = it to true }
        }
    }

}