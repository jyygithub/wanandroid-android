package com.jiangyy.wanandroid.logic

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.ui.adapter.AdapterViewHolder
import com.jiangyy.wanandroid.ui.main.PageViewModel

class Bean<T>(
    val errorCode: Int?,
    val errorMsg: String?,
    val data: T?,
)

class PageData<T>(
    val curPage: Int?,
    val offset: Int?,
    val datas: MutableList<T>,
    val over: Boolean?,
    val pageCount: Int?,
    val size: Int?,
    val total: Int?,
)

fun <T, VB : ViewBinding> BaseLoadFragment<VB>.loadData(
    it: Pair<Boolean, Result<PageData<T>>>,
    adapter: BaseQuickAdapter<T, AdapterViewHolder>,
    refreshLayout: SwipeRefreshLayout,
    viewModel: PageViewModel<T>
) {
    if (it.first) { // loadMore
        if (it.second.isSuccess) {
            adapter.addData(it.second.getOrNull()?.datas!!)
            if (adapter.data.size == it.second.getOrNull()?.total) {
                adapter.loadMoreModule.loadMoreEnd()
            } else {
                adapter.loadMoreModule.loadMoreComplete()
                viewModel.increasePage()
            }
        } else {
            adapter.loadMoreModule.loadMoreFail()
        }
    } else { // refresh
        preLoadSuccess()
        adapter.setList(null)
        refreshLayout.isRefreshing = false
        if (it.second.isSuccess) {
            adapter.addData(it.second.getOrNull()?.datas!!)
            if (adapter.data.size == it.second.getOrNull()?.total) {
                adapter.loadMoreModule.loadMoreEnd()
            } else {
                adapter.loadMoreModule.loadMoreComplete()
                viewModel.increasePage()
            }
        } else {
            preLoadWithFailure(it.second?.exceptionOrNull()?.message.orEmpty()) { preLoad() }
        }
    }
}