package com.jiangyy.wanandroid.ui.todo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.jiangyy.common.adapter.FooterAdapter
import com.jiangyy.common.utils.orZero
import com.jiangyy.common.view.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.FragmentTodosBinding
import com.jiangyy.wanandroid.ui.adapter.TodoAdapter
import com.jiangyy.wanandroid.utils.intArgument
import kotlinx.coroutines.launch

class TodosFragment : BaseLoadFragment<FragmentTodosBinding>(FragmentTodosBinding::inflate) {

    override val viewBindStatus: View get() = binding.refreshLayout

    private val mStatus by intArgument("status")

    private val mAdapter = TodoAdapter()

    override fun initWidget() {
        super.initWidget()
        binding.recyclerView.adapter = mAdapter.withLoadStateFooter(
            FooterAdapter { mAdapter.retry() }
        )
        mAdapter.addLoadStateListener {
            binding.refreshLayout.isRefreshing = false
            when (it.refresh) {
                is LoadState.NotLoading -> preLoadSuccess()
//                is LoadState.Loading -> preLoading()
                is LoadState.Error -> preLoadError {
                    binding.recyclerView.swapAdapter(mAdapter, true)
                    mAdapter.refresh()
                }

                else -> Unit
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.recyclerView.swapAdapter(mAdapter, true)
            mAdapter.refresh()
        }
    }

    override fun preLoad() {
        super.preLoad()
        val viewModel by viewModels<TodosViewModel>()
        lifecycleScope.launch {
            viewModel.pageTodo(mStatus.orZero()).collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(status: Int) =
            TodosFragment().apply {
                arguments = Bundle().apply {
                    putInt("status", status)
                }
            }
    }
}