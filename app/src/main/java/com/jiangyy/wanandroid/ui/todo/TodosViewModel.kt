package com.jiangyy.wanandroid.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiangyy.wanandroid.data.PagesSource
import com.jiangyy.wanandroid.entity.Todo
import com.jiangyy.wanandroid.logic.*
import kotlinx.coroutines.flow.Flow

class TodosViewModel : ViewModel() {

    fun pageTodo(status: Int): Flow<PagingData<Todo>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Todo>(1) {
                    override suspend fun request(page: Int): Bean<PageData<Todo>> {
                        return API_SERVICE.pageTodo(page, status)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    private val _delete = ResultMutableLiveData<Any>()

    val delete get() = _delete

    fun deleteTodo(id: Int) {
        flowRequest {
            request { API_SERVICE.deleteTodo(id) }
            response { _delete.value = it }
        }
    }

}