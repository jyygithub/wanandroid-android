package com.jiangyy.wanandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import com.jiangyy.viewbinding.adapter.FooterAdapter
import com.jiangyy.viewbinding.widget.MultipleStateLayout
import com.jiangyy.viewbinding.widget.MultipleStateListener
import com.jiangyy.wanandroid.databinding.FragmentBaseArticlesBinding
import com.jiangyy.wanandroid.ui.adapter.NewArticleAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity

abstract class BaseArticlesFragment : Fragment(), MultipleStateListener {

    private var _binding: FragmentBaseArticlesBinding? = null
    protected val binding get() = _binding!!

    private var multipleStateLayout: MultipleStateLayout? = null
    protected val mAdapter = NewArticleAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBaseArticlesBinding.inflate(inflater, container, false)

        multipleStateLayout = MultipleStateLayout(requireContext())
        val fragmentLayout = FrameLayout(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            )
        }
        fragmentLayout.addView(
            binding.root, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        fragmentLayout.addView(
            multipleStateLayout, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        return fragmentLayout
    }

    open fun initValue() {}

    open fun initWidget() {
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
                is LoadState.Error -> preLoadWithFailure {
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

    abstract fun initObserver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValue()
        initWidget()
        initObserver()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun customMultipleState(block: MultipleStateLayout.() -> Unit) {
        if (multipleStateLayout == null) return
        block.invoke(multipleStateLayout!!)
    }

    override fun setMultipleStateImage(emptyId: Int, failureId: Int, networkId: Int) {
        multipleStateLayout?.setThreeImage(emptyId, failureId, networkId)
    }

    override fun preLoadSuccess() {
        multipleStateLayout?.showSuccess()
    }

    override fun preLoading(text: CharSequence?) {
        multipleStateLayout?.showLoading(text)
    }

    override fun preLoadWithNetworkError(text: CharSequence?, block: (() -> Unit)?) {
        multipleStateLayout?.showNetwork(text, block)
    }

    override fun preLoadWithFailure(text: CharSequence?, block: (() -> Unit)?) {
        multipleStateLayout?.showFailure(text, block)
    }

    override fun preLoadWithEmpty(text: CharSequence?) {
        multipleStateLayout?.showEmpty(text)
    }

}