package com.jiangyy.wanandroid.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.hideSoftInput
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivitySearchBinding
import com.jiangyy.wanandroid.logic.ArticleUrl
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter
import com.jiangyy.wanandroid.ui.adapter.HotKeyAdapter
import com.jiangyy.wanandroid.ui.adapter.SearchHistoryAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import com.jiangyy.wanandroid.utils.DataStoreUtils
import com.jiangyy.wanandroid.utils.Firewood
import kotlinx.coroutines.launch
import rxhttp.awaitResult

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
    }

    override fun preLoad() {
        lifecycleScope.launch {
            ArticleUrl.hotKey()
                .awaitResult {
                    mHotKeyAdapter.setList(it.data)
                }
                .onFailure {

                }
        }

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
        lifecycleScope.launch {
            ArticleUrl.search(mPage, mKey!!)
                .awaitResult {
                    binding.contentArticles.refreshLayout.isRefreshing = false
                    if (it.isSuccess()) {
                        if (it.data?.datas.isNullOrEmpty()) {
                            preLoadWithEmpty("暂无数据")
                        } else {
                            preLoadSuccess()
                            mAdapter.addData(it.data?.datas!!)
                            if (mAdapter.data.size == it.data.total) {
                                mAdapter.loadMoreModule.loadMoreEnd()
                            } else {
                                mAdapter.loadMoreModule.loadMoreComplete()
                                ++mPage
                            }
                        }
                    } else {
                        preLoadWithFailure(it.errorMsg.orEmpty()) {
                            preLoad()
                        }
                    }
                }
                .onFailure {
                    binding.contentArticles.refreshLayout.isRefreshing = false
                    preLoadWithFailure {
                        preLoad()
                    }
                }
        }
    }

    private fun loadMore() {
        if (mKey.isNullOrBlank()) return
        lifecycleScope.launch {
            ArticleUrl.search(mPage, mKey!!)
                .awaitResult {
                    if (it.isSuccess()) {
                        if (it.data?.datas.isNullOrEmpty()) {
                            mAdapter.loadMoreModule.loadMoreEnd()
                        } else {
                            mAdapter.addData(it.data?.datas!!)
                            if (mAdapter.data.size == it.data.total) {
                                mAdapter.loadMoreModule.loadMoreEnd()
                            } else {
                                mAdapter.loadMoreModule.loadMoreComplete()
                                ++mPage
                            }
                        }
                    } else {
                        mAdapter.loadMoreModule.loadMoreFail()
                    }
                }
                .onFailure {
                    mAdapter.loadMoreModule.loadMoreFail()
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