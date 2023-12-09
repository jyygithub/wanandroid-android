package com.jiangyy.wanandroid.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.entity.Article
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class PagingFlowAction<T> {

    var request: (suspend (Int) -> ApiResponse<ApiResponse.Paging<T>>)? = null
        private set

    /**
     * 网络请求获取数据
     */
    fun request(block: suspend (Int) -> ApiResponse<ApiResponse.Paging<T>>) {
        request = block
    }

    var onEmpty: (suspend () -> Unit)? = null

    /**
     * 总数据为空
     */
    fun onEmpty(block: suspend () -> Unit) {
        onEmpty = block
    }

    var onFinish: (suspend (List<T>?) -> Unit)? = null

    /**
     * 首次加载完成（且有数据）
     */
    fun onFinish(block: suspend (List<T>?) -> Unit) {
        onFinish = block
    }

    var onError: (suspend (Exception) -> Unit)? = null

    /**
     * 首次加载失败
     */
    fun onError(block: suspend (Exception) -> Unit) {
        onError = block
    }

    var onLoadError: (suspend (Exception) -> Unit)? = null

    /**
     * 加载更多失败
     */
    fun onLoadError(block: suspend (Exception) -> Unit) {
        onLoadError = block
    }

    var onLoadFinish: (suspend (List<T>, Boolean) -> Unit)? = null

    /**
     * 加载更多完成
     */
    fun onLoadFinish(block: suspend (List<T>, Boolean) -> Unit) {
        onLoadFinish = block
    }

    var onCurrentPage: (suspend (Int) -> Unit)? = null

    /**
     * 获取当前的Page
     */
    fun onCurrentPage(block: suspend (Int) -> Unit) {
        onCurrentPage = block
    }
}

fun <T> LifecycleOwner.flowPagingRequest(requestPage: Int, startPage: Int, block: PagingFlowAction<T>.() -> Unit) {
    lifecycleScope.launch {
        val action = PagingFlowAction<T>().apply(block)
        var currentPage: Int = requestPage
        flow {
            emit(action.request!!.invoke(requestPage))
        }.onStart {
            currentPage = requestPage
        }.catch {
            when (it) {
                is UnknownHostException -> Exception("网络连接失败")
                else -> Exception(it.message.orEmpty())
            }.let { exception ->
                if (requestPage == startPage) {
                    // 第一次加载失败
                    action.onError!!.invoke(exception)
                } else {
                    // 加载更多失败
                    action.onLoadError!!.invoke(exception)
                }
            }
        }.collectLatest {
            if (it.errorCode == 0) {
                // 数据获取成功
                if (it.data.total == 0) {
                    // 没有数据
                    action.onEmpty!!.invoke()
                } else {
                    // 数据加载完成
                    if (it.data.curPage == 1) {
                        action.onFinish!!.invoke(it.data.datas)
                    }
                    action.onLoadFinish!!.invoke(it.data.datas, it.data.curPage >= it.data.pageCount)
                    action.onCurrentPage!!.invoke(++currentPage)
                }
            } else {
                if (requestPage == startPage) {
                    // 第一次加载失败
                    action.onError!!.invoke(Exception(it.errorMsg.orEmpty()))
                } else {
                    // 加载更多失败
                    action.onLoadError!!.invoke(Exception(it.errorMsg.orEmpty()))
                }
            }
        }
    }
}