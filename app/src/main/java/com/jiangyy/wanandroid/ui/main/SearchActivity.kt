package com.jiangyy.wanandroid.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.jiangyy.common.view.BaseLoadActivity
import com.jiangyy.common.adapter.FooterAdapter
import com.jiangyy.wanandroid.databinding.ActivitySearchBinding
import com.jiangyy.wanandroid.ui.adapter.HotKeyAdapter
import com.jiangyy.wanandroid.ui.adapter.NewArticleAdapter
import com.jiangyy.wanandroid.ui.adapter.SearchHistoryAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import com.jiangyy.wanandroid.utils.DataStoreUtils
import kotlinx.coroutines.launch

class SearchActivity : BaseLoadActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate), View.OnFocusChangeListener {

    override val viewBindStatus: View
        get() = binding.contentArticles.recyclerView

    private val mAdapter = NewArticleAdapter()

    private val mHotKeyAdapter = HotKeyAdapter()
    private val mSearchHistoryAdapter = SearchHistoryAdapter()

    override fun initWidget() {
        super.initWidget()
        binding.contentArticles.recyclerView.adapter = mAdapter.withLoadStateFooter(
            FooterAdapter { mAdapter.retry() }
        )
        mAdapter.addLoadStateListener {
            binding.contentArticles.refreshLayout.isRefreshing = false
            when (it.refresh) {
                is LoadState.NotLoading -> preLoadSuccess()
//                is LoadState.Loading -> preLoading()
                is LoadState.Error -> preLoadError {
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
        mAdapter.itemClick { position ->
            ArticleActivity.actionStart(this, mAdapter.peek(position))
        }
        binding.contentArticles.refreshLayout.setOnRefreshListener {
            binding.contentArticles.recyclerView.swapAdapter(mAdapter, true)
            mAdapter.refresh()
        }
        binding.etSearch.onFocusChangeListener = this
        binding.contentSearch.recyclerViewHot.adapter = mHotKeyAdapter
        binding.contentSearch.recyclerViewHistory.adapter = mSearchHistoryAdapter
        mHotKeyAdapter.itemClick { position ->
            mKey = mHotKeyAdapter.getItem(position).name
            search()
        }
        mSearchHistoryAdapter.itemClick { position ->
            mKey = mSearchHistoryAdapter.getItem(position)
            search()
        }

    }

    private val mViewModel by viewModels<SearchViewModel>()

    override fun initObserver() {
        super.initObserver()
        mViewModel.hotKey.observe(this) {
            mHotKeyAdapter.submitList = it.getOrNull()
        }
    }

    override fun preLoad() {
        super.preLoad()
        mViewModel.hotKey()
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus) {
            mSearchHistoryAdapter.submitList = DataStoreUtils.getSearchHistory()
            binding.contentSearch.rootView.visibility = View.VISIBLE
        } else {
            binding.contentSearch.rootView.visibility = View.GONE
        }
    }

    private var mKey: String? = null

    private fun search() {
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