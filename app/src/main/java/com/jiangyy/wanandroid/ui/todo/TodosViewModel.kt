package com.jiangyy.wanandroid.ui.todo

import com.jiangyy.wanandroid.data.PageViewModel
import com.jiangyy.wanandroid.entity.Todo
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData

class TodosViewModel : PageViewModel<Todo>() {

    private var mStatus = 0

    fun fetchStatus(status: Int) {
        mStatus = status
        firstLoad()
    }

    override suspend fun realRequest(page: Int): Bean<PageData<Todo>> {
        return API_SERVICE.pageTodo(page, mStatus)
    }

}