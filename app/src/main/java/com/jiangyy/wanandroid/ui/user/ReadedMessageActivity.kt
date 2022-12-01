package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityReadedMessageBinding
import com.jiangyy.wanandroid.logic.UserUrl
import com.jiangyy.wanandroid.ui.adapter.MessageAdapter
import kotlinx.coroutines.launch
import rxhttp.awaitResult

class ReadedMessageActivity : BaseLoadActivity<ActivityReadedMessageBinding>(), MultipleStateModule {

    private val mAdapter = MessageAdapter()

    private var mPage = 1

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            loadMore()
        }
    }

    override fun preLoad() {
        refresh()
    }

    private fun refresh() {
        mPage = 1
        mAdapter.setList(null)
        lifecycleScope.launch {
            UserUrl.listReadedMessage(1)
                .awaitResult {
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
                    it.printStackTrace()
                    preLoadWithFailure {
                        preLoad()
                    }
                }
        }
    }

    private fun loadMore() {
        lifecycleScope.launch {
            UserUrl.listReadedMessage(mPage)
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
            context.startActivity(Intent(context, ReadedMessageActivity::class.java))
        }
    }

}