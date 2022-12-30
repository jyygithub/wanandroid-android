package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiangyy.wanandroid.data.PagesSource
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.netRequest
import kotlinx.coroutines.flow.Flow

class ShareHistoryViewModel : ViewModel() {

    private val _unshare = MutableLiveData<Result<Any?>>()

    val unshare get() = _unshare

    fun unshare(id: String) {
        netRequest {
            request { API_SERVICE.unshare(id) }
            success { _unshare.value = Result.success(it) }
            error { _unshare.value = Result.failure(it) }
        }
    }

    // ---

    fun listShareHistory(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Article>(1) {
                    override suspend fun request(page: Int): Bean<PageData<Article>> {
                        return API_SERVICE.listShareHistory(page)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

}