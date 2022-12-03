package com.jiangyy.wanandroid.ui.todo

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.FragmentTodosBinding
import com.jiangyy.wanandroid.logic.TodoUrl
import com.jiangyy.wanandroid.ui.adapter.TodoAdapter
import kotlinx.coroutines.launch
import rxhttp.awaitResult

private const val ARG_PARAM1 = "status"

class TodosFragment : BaseLoadFragment<FragmentTodosBinding>(), MultipleStateModule {

    private var param1: Int? = null
    private val mAdapter = TodoAdapter()
    private var mPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            loadMore()
        }
    }

    override fun preLoad() {
        refresh()
    }

    private fun refresh() {
        mPage = 0
        mAdapter.setList(null)
        lifecycleScope.launch {
            TodoUrl.pageTodo(mPage, param1)
                .awaitResult {
                    binding.refreshLayout.isRefreshing = false
                    if (it.isSuccess()) {
                        if (it.data?.datas.isNullOrEmpty()) {
                            preLoadWithEmpty("暂无数据")
                        } else {
                            preLoadSuccess()
                            mAdapter.addData(it.data?.datas!!)
                            if (mAdapter.data.size == it.data.total) {
                                mAdapter.loadMoreModule.loadMoreEnd()
                            } else {
                                mAdapter.loadMoreModule.loadMoreComplete()
                                ++mPage
                            }
                        }
                    } else {
                        preLoadWithFailure(it.errorMsg.orEmpty()) {
                            preLoad()
                        }
                    }
                }
                .onFailure {
                    binding.refreshLayout.isRefreshing = false
                    preLoadWithFailure {
                        preLoad()
                    }
                }
        }
    }

    private fun loadMore() {
        lifecycleScope.launch {
            TodoUrl.pageTodo(mPage, param1)
                .awaitResult {
                    if (it.isSuccess()) {
                        if (it.data?.datas.isNullOrEmpty()) {
                            mAdapter.loadMoreModule.loadMoreEnd()
                        } else {
                            mAdapter.addData(it.data?.datas!!)
                            if (mAdapter.data.size == it.data.total) {
                                mAdapter.loadMoreModule.loadMoreEnd()
                            } else {
                                mAdapter.loadMoreModule.loadMoreComplete()
                                ++mPage
                            }
                        }
                    } else {
                        mAdapter.loadMoreModule.loadMoreFail()
                    }
                }
                .onFailure {
                    mAdapter.loadMoreModule.loadMoreFail()
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            TodosFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}