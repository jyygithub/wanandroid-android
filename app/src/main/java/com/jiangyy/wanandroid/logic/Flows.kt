package com.jiangyy.wanandroid.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ResultPageMutableLiveData<T> : MutableLiveData<Pair<Boolean, Result<PageData<T>>>>()
class ResultMutableLiveData<T> : MutableLiveData<Result<T>>()
class ResultLiveData<T> : LiveData<Result<T>>()

class NoLoginException() : Exception("用户未登录")
class NullDataException() : Exception("数据为空")

public val <T> Result<T>.isSuccessOrNull: Boolean get() = isSuccess || exceptionOrNull() is NullDataException

class FlowAction<T> {
    var request: (suspend () -> Bean<T>)? = null
        private set

    fun request(block: suspend () -> Bean<T>) {
        request = block
    }

    var response: ((Result<T>) -> Unit)? = null
        private set

    fun response(block: (Result<T>) -> Unit) {
        response = block
    }
}

fun <T> ViewModel.flowRequest(block: FlowAction<T>.() -> Unit) {
    val action = FlowAction<T>().apply(block)
    viewModelScope.launch {
        flow {
            emit(action.request!!.invoke())
        }.catch {
            action.response!!.invoke(Result.failure(it))
        }.collectLatest {
            if (it.errorCode == 0) {
                if (it.data != null) {
                    action.response!!.invoke(Result.success(it.data))
                } else {
                    action.response!!.invoke(Result.failure(NullDataException()))
                }
            } else {
                action.response!!.invoke(Result.failure(Exception(it.errorMsg.orEmpty())))
            }
        }
    }
}