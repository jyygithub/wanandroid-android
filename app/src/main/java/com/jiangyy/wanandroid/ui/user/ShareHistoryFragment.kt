package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.dialog.StringBottomListDialog
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.ContentArticlesBinding
import com.jiangyy.wanandroid.logic.ArticleUrl
import com.jiangyy.wanandroid.logic.UserUrl
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity
import kotlinx.coroutines.launch
import rxhttp.awaitResult

class ShareHistoryFragment : BaseLoadFragment<ContentArticlesBinding>(), MultipleStateModule {

    private val mAdapter = ArticleAdapter()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(requireActivity(), mAdapter.getItem(position))
        }
        mAdapter.setOnItemLongClickListener { _, _, position ->
            StringBottomListDialog()
                .bindConfig { title = "文章操作" }
                .items("取消分享") { _, _ ->
                    unshare(position)
                }
                .show(childFragmentManager)

            false
        }
        binding.refreshLayout.setOnRefreshListener {
            refresh()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            loadMore()
        }
    }

    override fun preLoad() {
        refresh()
    }

    private fun unshare(position: Int) {
        val article = mAdapter.getItem(position)
        lifecycleScope.launch {
            ArticleUrl.unshare(article.id.orEmpty())
                .awaitResult {
                    if (it.isSuccess()) {
                        doneToast("取消分享成功")
                        mAdapter.removeAt(position)
                    } else {
                        errorToast(it.errorMsg.orEmpty())
                    }
                }
                .onFailure {
                    errorToast("取消分享失败")
                }
        }
    }

    private var mPage = 1

    private fun refresh() {
        mPage = 1
        mAdapter.setList(null)
        lifecycleScope.launch {
            UserUrl.listShareHistory(mPage)
                .awaitResult {
                    binding.refreshLayout.isRefreshing = false
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
                    binding.refreshLayout.isRefreshing = false
                    preLoadWithFailure {
                        preLoad()
                    }
                }
        }
    }

    private fun loadMore() {
        lifecycleScope.launch {
            UserUrl.listShareHistory(mPage)
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
        @JvmStatic
        fun newInstance() = ShareHistoryFragment()
    }

}