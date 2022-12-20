package com.jiangyy.wanandroid.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.jiangyy.core.hideSoftInput
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivitySearchBinding
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter
import com.jiangyy.wanandroid.ui.adapter.HotKeyAdapter
import com.jiangyy.wanandroid.ui.adapter.SearchHistoryAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import com.jiangyy.wanandroid.utils.DataStoreUtils

class SearchActivity : BaseLoadActivity<ActivitySearchBinding>(), View.OnFocusChangeListener {

    private val mAdapter = ArticleAdapter()

    private val mHotKeyAdapter = HotKeyAdapter()
    private val mSearchHistoryAdapter = SearchHistoryAdapter()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.contentArticles.recyclerView.adapter = mAdapter
        binding.toolbar.setOnEndListener {
            mKey = if (binding.etSearch.text.isNullOrBlank()) null else binding.etSearch.text.toString().trim()
            refresh()
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(this, mAdapter.getItem(position))
        }
        binding.contentArticles.refreshLayout.setOnRefreshListener {
            refresh()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            loadMore()
        }
        binding.etSearch.onFocusChangeListener = this
        binding.contentSearch.recyclerViewHot.adapter = mHotKeyAdapter
        binding.contentSearch.recyclerViewHistory.adapter = mSearchHistoryAdapter
        mHotKeyAdapter.setOnItemClickListener { _, _, position ->
            mKey = mHotKeyAdapter.getItem(position).name
            refresh()
        }
        mSearchHistoryAdapter.setOnItemClickListener { _, _, position ->
            mKey = mSearchHistoryAdapter.getItem(position)
            refresh()
        }
        mViewModel.hotKey.observe(this) {
            mHotKeyAdapter.setList(it)
        }
        mViewModel.firstData().observe(this) {
            binding.contentArticles.refreshLayout.isRefreshing = false
            mAdapter.setList(null)
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
            binding.contentArticles.refreshLayout.isRefreshing = false
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
                preLoadWithFailure(it.first.message.orEmpty()) {
                    preLoad()
                }
            }
        }
    }

    private val mViewModel by viewModels<SearchViewModel>()

    override fun preLoad() {
        mViewModel.hotKey()
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus) {
            mSearchHistoryAdapter.setList(DataStoreUtils.getSearchHistory())
            binding.contentSearch.rootView.visibility = View.VISIBLE
        } else {
            binding.contentSearch.rootView.visibility = View.GONE
        }
    }

    private var mPage = 0
    private var mKey: String? = null

    private fun refresh() {
        hideSoftInput()
        binding.etSearch.clearFocus()
        mPage = 0
        mAdapter.setList(null)
        if (mKey.isNullOrBlank()) return
        DataStoreUtils.search(mKey.orEmpty())
        binding.etSearch.setText(mKey)
        mViewModel.search(mKey.orEmpty())
    }

    private fun loadMore() {
        if (mKey.isNullOrBlank()) return
        mViewModel.loadMore()
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, SearchActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}