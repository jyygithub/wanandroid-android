package com.jiangyy.wanandroid.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData

abstract class PagesSource<T : Any>(private val firstPage: Int) : PagingSource<Int, T>() {

    abstract suspend fun request(page: Int): Bean<PageData<T>>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: firstPage
            val rspRepository = request(page)
            val items = rspRepository.data?.datas ?: mutableListOf()
            val preKey = if (page > 1) page - 1 else null
            val nextKey = if (rspRepository.data?.curPage == rspRepository.data?.pageCount) null else page + 1
            LoadResult.Page(items, preKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}