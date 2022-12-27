package com.jiangyy.wanandroid.ui.user

import androidx.fragment.app.viewModels
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.dialog.StringBottomListDialog
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.ContentArticlesBinding
import com.jiangyy.wanandroid.ui.adapter.ArticleAdapter
import com.jiangyy.wanandroid.ui.article.ArticleActivity

class ShareHistoryFragment : BaseLoadFragment<ContentArticlesBinding>(), MultipleStateModule {

    private val mAdapter = ArticleAdapter()

    private val mViewModel by viewModels<ShareHistoryViewModel>()
    private var mCurrentPosition = 0

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(requireActivity(), mAdapter.getItem(position))
        }
        mAdapter.setOnItemLongClickListener { _, _, position ->
            mCurrentPosition = position
            StringBottomListDialog()
                .bindConfig { title = "文章操作" }
                .items("取消分享") { _, _ ->
                    val article = mAdapter.getItem(position)
                    mViewModel.unshare(article.id.orEmpty())
                }
                .show(childFragmentManager)

            false
        }
        binding.refreshLayout.setOnRefreshListener {
            mViewModel.firstLoad()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMore()
        }
        mViewModel.firstData().observe(this) {
            mAdapter.setList(null)
            binding.refreshLayout.isRefreshing = false
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
            binding.refreshLayout.isRefreshing = false
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
                binding.refreshLayout.isRefreshing = false
                preLoadWithFailure(it.first.message.orEmpty()) {
                    preLoad()
                }
            }
        }
        mViewModel.unshare.observe(this) {
            if (it.isSuccess) {
                doneToast("取消分享成功")
                mAdapter.removeAt(mCurrentPosition)
            } else {
                errorToast(it.exceptionOrNull()?.message.orEmpty())
            }
        }
    }

    override fun preLoad() {
        mViewModel.firstLoad()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShareHistoryFragment()
    }

}