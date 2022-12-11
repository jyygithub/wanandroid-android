package com.jiangyy.wanandroid.ui.todo

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.jiangyy.core.orZero
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.FragmentTodosBinding
import com.jiangyy.wanandroid.ui.adapter.TodoAdapter

private const val ARG_PARAM1 = "status"

class TodosFragment : BaseLoadFragment<FragmentTodosBinding>(), MultipleStateModule {

    private var param1: Int? = null
    private val mAdapter = TodoAdapter()
    private val mViewModel by viewModels<TodosViewModel>()

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
            mViewModel.firstLoad()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMore()
        }
        mViewModel.firstData().observe(this) {
            mAdapter.setList(null)
            binding.refreshLayout.isRefreshing = false
            if (it.datas.isEmpty()) {
                preLoadWithEmpty("暂无数据")
            } else {
                preLoadSuccess()
                mAdapter.addData(it.datas)
                if (mAdapter.data.size == it.total) {
                    mAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    mAdapter.loadMoreModule.loadMoreComplete()
                    mViewModel.mPage++
                }
            }
        }
        mViewModel.loadMoreData().observe(this) {
            if (it.datas.isEmpty()) {
                mAdapter.loadMoreModule.loadMoreEnd()
            } else {
                mAdapter.addData(it.datas)
                if (mAdapter.data.size == it.total) {
                    mAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    mAdapter.loadMoreModule.loadMoreComplete()
                    mViewModel.mPage++
                }
            }
        }
        mViewModel.dataError().observe(this) {
            if (it.second) {
                mAdapter.loadMoreModule.loadMoreFail()
            } else {
                binding.refreshLayout.isRefreshing = false
                preLoadWithFailure(it.first.message.orEmpty()) {
                    preLoad()
                }
            }
        }
    }

    override fun preLoad() {
        mViewModel.fetchStatus(param1.orZero())
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