package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityReadedMessageBinding
import com.jiangyy.wanandroid.logic.loadData
import com.jiangyy.wanandroid.ui.adapter.MessageAdapter

class ReadedMessageActivity : BaseLoadActivity<ActivityReadedMessageBinding>(), MultipleStateModule {

    private val mAdapter = MessageAdapter()

    private val mViewModel by viewModels<ReadedMessageViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        binding.refreshLayout.setOnRefreshListener {
            mViewModel.firstLoad()
        }
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMore()
        }
        mViewModel.pageData.observe(this){
            this.loadData(it,mAdapter,binding.refreshLayout,mViewModel)
        }
    }

    override fun preLoad() {
        mViewModel.firstLoad()
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, ReadedMessageActivity::class.java))
        }
    }

}