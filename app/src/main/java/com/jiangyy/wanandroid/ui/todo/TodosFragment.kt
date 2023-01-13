package com.jiangyy.wanandroid.ui.todo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.jiangyy.common.adapter.FooterAdapter
import com.jiangyy.common.utils.doneToast
import com.jiangyy.common.utils.errorToast
import com.jiangyy.common.utils.orZero
import com.jiangyy.common.view.BaseLoadFragment
import com.jiangyy.dialog.ConfirmDialog
import com.jiangyy.wanandroid.databinding.FragmentTodosBinding
import com.jiangyy.wanandroid.logic.isSuccessOrNull
import com.jiangyy.wanandroid.ui.adapter.TodoAdapter
import com.jiangyy.wanandroid.utils.intArgument
import kotlinx.coroutines.launch

class TodosFragment : BaseLoadFragment<FragmentTodosBinding>(FragmentTodosBinding::inflate) {

    override val viewBindStatus: View get() = binding.refreshLayout

    private val mStatus by intArgument("status")

    private val mAdapter = TodoAdapter()

    private val mViewModel by activityViewModels<TodosViewModel>()

    override fun initWidget() {
        super.initWidget()
        mAdapter.itemClick {
            ConfirmDialog()
                .bindConfig {
                    title = "提示"
                    content = "确认删除该待办"
                }
                .confirm { _ ->
                    mViewModel.deleteTodo(mAdapter.peek(it)?.id.orZero())
                }
                .show(childFragmentManager)
        }
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

    override fun initObserver() {
        super.initObserver()
        mViewModel.delete.observe(this) {
            if (it.isSuccessOrNull) {
                doneToast("删除成功")
                preLoad()
            } else {
                errorToast(it.exceptionOrNull()?.message.orEmpty())
            }
        }
    }

    override fun preLoad() {
        super.preLoad()

        lifecycleScope.launch {
            mViewModel.pageTodo(mStatus.orZero()).collect { pagingData ->
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