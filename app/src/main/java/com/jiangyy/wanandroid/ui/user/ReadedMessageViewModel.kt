package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiangyy.wanandroid.data.PagesSource
import com.jiangyy.wanandroid.entity.Message
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData
import kotlinx.coroutines.flow.Flow

class ReadedMessageViewModel : ViewModel() {

    fun listReadedMessage(): Flow<PagingData<Message>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Message>(1) {
                    override suspend fun request(page: Int): Bean<PageData<Message>> {
                        return API_SERVICE.listReadedMessage(page)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

}