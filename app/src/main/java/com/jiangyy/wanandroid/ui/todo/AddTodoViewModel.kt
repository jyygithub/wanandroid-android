package com.jiangyy.wanandroid.ui.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.netRequest

class AddTodoViewModel : ViewModel() {

    private val _add = MutableLiveData<Result<Any?>>()

    val add get() = _add

    fun addTodo(title: String, content: String, date: String) {
        netRequest<Any> {
            request { API_SERVICE.addTodo(title, content, date) }
            success { _add.value = Result.success(it) }
            error { _add.value = Result.failure(it) }
        }
    }

}