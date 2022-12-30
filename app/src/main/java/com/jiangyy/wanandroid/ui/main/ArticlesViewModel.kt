package com.jiangyy.wanandroid.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jiangyy.wanandroid.data.PagesSource
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData
import kotlinx.coroutines.flow.Flow

class ArticlesViewModel : ViewModel() {

    fun pageHomeArticles(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Article>(0) {
                    override suspend fun request(page: Int): Bean<PageData<Article>> {
                        return API_SERVICE.pageHomeArticle(page)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun pageHomeProject(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Article>(0) {
                    override suspend fun request(page: Int): Bean<PageData<Article>> {
                        return API_SERVICE.pageHomeProject(page)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun listSquare(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Article>(0) {
                    override suspend fun request(page: Int): Bean<PageData<Article>> {
                        return API_SERVICE.listSquare(page)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun pageArticleInTree(treeId: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Article>(0) {
                    override suspend fun request(page: Int): Bean<PageData<Article>> {
                        return API_SERVICE.pageArticleInTree(page, treeId)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun listArticleInWechat(wechatId: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Article>(1) {
                    override suspend fun request(page: Int): Bean<PageData<Article>> {
                        return API_SERVICE.listArticleInWechat(page, wechatId)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun listWenda(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Article>(1) {
                    override suspend fun request(page: Int): Bean<PageData<Article>> {
                        return API_SERVICE.listWenda(page)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun listArticleInSub(subId: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(25),
            pagingSourceFactory = {
                object : PagesSource<Article>(0) {
                    override suspend fun request(page: Int): Bean<PageData<Article>> {
                        return API_SERVICE.listArticleInSub(page, subId)
                    }
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

}