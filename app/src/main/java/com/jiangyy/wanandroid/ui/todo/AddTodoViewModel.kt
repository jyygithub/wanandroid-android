package com.jiangyy.wanandroid.ui.todo

import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.ResultMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest

class AddTodoViewModel : ViewModel() {

    private val _add = ResultMutableLiveData<Any>()

    val add get() = _add

    fun addTodo(title: String, content: String, date: String) {
        flowRequest {
            request { API_SERVICE.addTodo(title, content, date) }
            response { _add.value = it }
        }
    }

}