package com.jiangyy.wanandroid.ui.home.sub

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.TreeAdapter
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.FragmentTreeBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.article.ArticlesActivity
import com.koonny.appcompat.BaseFragment
import com.koonny.appcompat.module.StatusModule

class TreeFragment : BaseFragment<FragmentTreeBinding>(FragmentTreeBinding::inflate), StatusModule {

    private val mAdapter = TreeAdapter()

    override fun viewBindStatus(): View {
        return binding.recyclerView
    }

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mAdapter.getItemViewType(position) == 0) 2 else 1
                }
            }
        }
        binding.recyclerView.adapter = mAdapter
        binding.refreshLayout.setOnRefreshListener {
            onPrepareData()
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItem(position)?.let {
                if (it.itemType == 1) {
                    ArticlesActivity.actionStart(requireActivity(), "tree", it)
                }
            }
        }
    }

    override fun onStatusRetry() {
        onPrepareData()
    }

    override fun onPrepareData() {
        super.onPrepareData()
        flowRequest<MutableList<Tree>> {
            request {
                startLoading()
                RetrofitHelper.getInstance().create(Api::class.java).tree()
            }
            response {
                binding.refreshLayout.isRefreshing = false
                val result = mutableListOf<Tree>()
                if (it.isSuccess) {
                    finishLoading()
                    it.getOrNull()?.forEach { parent ->
                        result.add(parent)
                        parent.children?.forEach { children ->
                            result.add(children)
                        }
                    }
                    mAdapter.submitList(result)
                } else {
                    finishLoadingWithStatus(it.exceptionOrNull()?.message.orEmpty(), R.drawable.ic_state_failure)
                }
            }
        }
    }

    companion object {
        fun newInstance() = TreeFragment()
    }

}