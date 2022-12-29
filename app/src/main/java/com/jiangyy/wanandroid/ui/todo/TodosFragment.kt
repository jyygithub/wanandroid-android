package com.jiangyy.wanandroid.ui.todo

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.jiangyy.core.orZero
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.FragmentTodosBinding
import com.jiangyy.wanandroid.logic.loadData
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
        mViewModel.pageData.observe(this){
            this.loadData(it,mAdapter,binding.refreshLayout,mViewModel)
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