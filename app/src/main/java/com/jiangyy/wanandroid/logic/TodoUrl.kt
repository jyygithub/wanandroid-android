package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Todo
import rxhttp.toAwait
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

class TodoUrl {

    companion object {
        suspend fun pageTodo(page: Int): Await<Beans<Todo>> {
            return RxHttp.get("lg/todo/v2/list/$page/json")
                .toAwait()
        }

    }

}