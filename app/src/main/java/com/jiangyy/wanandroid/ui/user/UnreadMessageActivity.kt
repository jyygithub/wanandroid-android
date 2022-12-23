package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jiangyy.core.click
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityUnreadMessageBinding
import com.jiangyy.wanandroid.ui.adapter.MessageAdapter

class UnreadMessageActivity : BaseLoadActivity<ActivityUnreadMessageBinding>() {

    private val mAdapter = MessageAdapter()

    private val mViewModel by viewModels<UnreadMessageViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        binding.tvReaded.click {
            ReadedMessageActivity.actionStart(this)
        }
        mViewModel.messages.observe(this){
            mAdapter.setList(it.getOrNull()?.datas)
        }
    }

    override fun preLoad() {
        mViewModel.listMessage()

    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, UnreadMessageActivity::class.java))
        }
    }

}