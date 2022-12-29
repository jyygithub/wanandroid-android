package com.jiangyy.wanandroid.data

import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.ResultPageMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest

abstract class PageViewModel<T> : ViewModel() {

    protected open var firstPage = 0

    private var page = 0

    private val _pageData = ResultPageMutableLiveData<T>()
    val pageData = _pageData

    fun firstLoad() {
        page = firstPage
        flowRequest {
            request { realRequest(page) }
            response { _pageData.value = false to it }
        }
    }

    fun loadMore() {
        flowRequest {
            request { realRequest(page) }
            response { _pageData.value = true to it }
        }
    }

    fun increasePage() = page++

    protected abstract suspend fun realRequest(page: Int): Bean<PageData<T>>

}