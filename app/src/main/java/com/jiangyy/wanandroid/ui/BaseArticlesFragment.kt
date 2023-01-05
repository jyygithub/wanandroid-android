package com.jiangyy.wanandroid.ui

import android.view.View
import androidx.paging.LoadState
import com.jiangyy.common.view.BaseLoadFragment
import com.jiangyy.viewbinding.adapter.FooterAdapter
import com.jiangyy.wanandroid.databinding.FragmentBaseArticlesBinding
import com.jiangyy.wanandroid.ui.adapter.NewArticleAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity

abstract class BaseArticlesFragment : BaseLoadFragment<FragmentBaseArticlesBinding>(FragmentBaseArticlesBinding::inflate) {

    protected val mAdapter = NewArticleAdapter()

    override val viewBindStatus: View get() = binding.refreshLayout

    override fun initWidget() {
        super.initWidget()
        mAdapter.setOnItemClickListener {
            ArticleActivity.actionStart(requireActivity(), mAdapter.peek(it))
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

}