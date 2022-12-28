package com.jiangyy.wanandroid.ui.main

import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData

class ProjectsViewModel : PageViewModel<Article>() {

    override suspend fun realRequest(page: Int): Bean<PageData<Article>> {
        return API_SERVICE.pageHomeProject(page)
    }

}