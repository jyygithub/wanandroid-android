package com.jiangyy.wanandroid.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiangyy.wanandroid.data.PageViewModel
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData
import kotlinx.coroutines.flow.Flow

class ArticlesViewModel : PageViewModel<Article>() {

    override suspend fun realRequest(page: Int): Bean<PageData<Article>> {
        return API_SERVICE.pageHomeArticle(page)
    }

    fun getPagingData(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = { ArticlesPagingSource() }
        ).flow.cachedIn(viewModelScope)
    }

}