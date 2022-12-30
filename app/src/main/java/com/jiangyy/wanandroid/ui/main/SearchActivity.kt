package com.jiangyy.wanandroid.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.jiangyy.core.hideSoftInput
import com.jiangyy.viewbinding.adapter.FooterAdapter
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivitySearchBinding
import com.jiangyy.wanandroid.ui.adapter.HotKeyAdapter
import com.jiangyy.wanandroid.ui.adapter.NewArticleAdapter
import com.jiangyy.wanandroid.ui.adapter.SearchHistoryAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import com.jiangyy.wanandroid.utils.DataStoreUtils
import kotlinx.coroutines.launch

class SearchActivity : BaseLoadActivity<ActivitySearchBinding>(), View.OnFocusChangeListener {

    private val mAdapter = NewArticleAdapter()

    private val mHotKeyAdapter = HotKeyAdapter()
    private val mSearchHistoryAdapter = SearchHistoryAdapter()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.contentArticles.recyclerView.adapter = mAdapter.withLoadStateFooter(
            FooterAdapter { mAdapter.retry() }
        )
        mAdapter.addLoadStateListener {
            binding.contentArticles.refreshLayout.isRefreshing = false
            when (it.refresh) {
                is LoadState.NotLoading -> preLoadSuccess()
//                is LoadState.Loading -> preLoading()
                is LoadState.Error -> preLoadWithFailure {
                    binding.contentArticles.recyclerView.swapAdapter(mAdapter, true)
                    mAdapter.refresh()
                }

                else -> Unit
            }
        }
        binding.toolbar.setOnEndListener {
            mKey = if (binding.etSearch.text.isNullOrBlank()) null else binding.etSearch.text.toString().trim()
            search()
        }
        mAdapter.setOnItemClickListener { position ->
            ArticleActivity.actionStart(this, mAdapter.peek(position))
        }
        binding.contentArticles.refreshLayout.setOnRefreshListener {
            binding.contentArticles.recyclerView.swapAdapter(mAdapter, true)
            mAdapter.refresh()
        }
        binding.etSearch.onFocusChangeListener = this
        binding.contentSearch.recyclerViewHot.adapter = mHotKeyAdapter
        binding.contentSearch.recyclerViewHistory.adapter = mSearchHistoryAdapter
        mHotKeyAdapter.setOnItemClickListener { position ->
            mKey = mHotKeyAdapter.currentList[position].name
            search()
        }
        mSearchHistoryAdapter.setOnItemClickListener { position ->
            mKey = mSearchHistoryAdapter.currentList[position]
            search()
        }
        mViewModel.hotKey.observe(this) {
            mHotKeyAdapter.submitList(it)
        }
    }

    private val mViewModel by viewModels<SearchViewModel>()

    override fun initObserver() {

    }

    override fun preLoad() {
        mViewModel.hotKey()
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus) {
            mSearchHistoryAdapter.submitList(DataStoreUtils.getSearchHistory())
            binding.contentSearch.rootView.visibility = View.VISIBLE
        } else {
            binding.contentSearch.rootView.visibility = View.GONE
        }
    }

    private var mKey: String? = null

    private fun search() {
        hideSoftInput()
        binding.etSearch.clearFocus()
        if (mKey.isNullOrBlank()) return
        DataStoreUtils.search(mKey.orEmpty())
        binding.etSearch.setText(mKey)
        lifecycleScope.launch {
            mViewModel.search(mKey.orEmpty()).collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, SearchActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}