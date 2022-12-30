package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiangyy.wanandroid.data.PagesSource
import com.jiangyy.wanandroid.entity.CoinHistory
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData
import kotlinx.coroutines.flow.Flow

class CoinHistoryViewModel : ViewModel() {

    fun pageCoinHistory(): Flow<PagingData<CoinHistory>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<CoinHistory>(1) {
                    override suspend fun request(page: Int): Bean<PageData<CoinHistory>> {
                        return API_SERVICE.pageCoinHistory(page)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

}