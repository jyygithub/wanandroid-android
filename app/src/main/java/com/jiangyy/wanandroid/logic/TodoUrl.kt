package com.jiangyy.wanandroid.logic

import com.jiangyy.wanandroid.entity.Todo
import rxhttp.toAwait
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

class TodoUrl {

    companion object {
        suspend fun pageTodo(page: Int, status: Int?): Await<Beans<Todo>> {
            return RxHttp.get("lg/todo/v2/list/$page/json")
                .addQuery("status", status)
                .toAwait()
        }

        suspend fun addTodo(title: String, content: String, date: String): Await<Beans<Any>> {
            return RxHttp.postForm("lg/todo/add/json")
                .add("title", title)
                .add("content", content)
                .add("date", date)
                .toAwait()
        }

    }

}