package com.jiangyy.wanandroid.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiangyy.wanandroid.data.PagesSource
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.HotKey
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.netRequest
import kotlinx.coroutines.flow.Flow

class SearchViewModel : ViewModel() {

    private val _hotKey = MutableLiveData<MutableList<HotKey>>()

    val hotKey get() = _hotKey

    fun hotKey() {
        netRequest {
            request { API_SERVICE.hotKey() }
            success { _hotKey.value = it }
            error { }
        }
    }

    // -- 搜索

    fun search(key: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Article>(0) {
                    override suspend fun request(page: Int): Bean<PageData<Article>> {
                        return API_SERVICE.search(page, key)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

}