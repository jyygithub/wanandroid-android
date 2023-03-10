package com.jiangyy.wanandroid.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class FlowAction<T> {

    var start: (suspend () -> Unit)? = null
        private set

    fun start(block: suspend () -> Unit) {
        start = block
    }

    var completion: (suspend () -> Unit)? = null
        private set

    fun completion(block: suspend () -> Unit) {
        completion = block
    }

    var request: (suspend () -> ApiResponse<T>)? = null
        private set

    fun request(block: suspend () -> ApiResponse<T>) {
        request = block
    }

    var response: ((Result<T?>) -> Unit)? = null
        private set

    fun response(block: (Result<T?>) -> Unit) {
        response = block
    }
}

fun <T> LifecycleOwner.flowRequest(block: FlowAction<T>.() -> Unit) {
    lifecycleScope.launch {
        val action = FlowAction<T>().apply(block)
        flow {
            emit(action.request!!.invoke())
        }.onStart {
            action.start?.invoke()
        }.catch {
            when (it) {
                is UnknownHostException -> action.response!!.invoke(Result.failure(Exception("网络连接失败")))
                else -> action.response!!.invoke(Result.failure(Exception(it.message)))
            }
        }.onCompletion {
            action.completion?.invoke()
        }.collect {
            if (it.errorCode == 0) {
                action.response!!.invoke(Result.success(it.data))
            } else {
                action.response!!.invoke(Result.failure(Exception(it.errorMsg.orEmpty())))
            }
        }
    }
}