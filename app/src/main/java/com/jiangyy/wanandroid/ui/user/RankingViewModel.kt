package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiangyy.wanandroid.data.PagesSource
import com.jiangyy.wanandroid.entity.Coin
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData
import kotlinx.coroutines.flow.Flow

class RankingViewModel : ViewModel() {

    fun ranking(): Flow<PagingData<Coin>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Coin>(1) {
                    override suspend fun request(page: Int): Bean<PageData<Coin>> {
                        return API_SERVICE.ranking(page)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

}