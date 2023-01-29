package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jiangyy.common.utils.click
import com.jiangyy.common.view.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityUnreadMessageBinding
import com.jiangyy.wanandroid.ui.adapter.UnreadMessageAdapter

class UnreadMessageActivity : BaseActivity<ActivityUnreadMessageBinding>(ActivityUnreadMessageBinding::inflate) {

    private val mAdapter = UnreadMessageAdapter()

    private val mViewModel by viewModels<UnreadMessageViewModel>()

    override fun initWidget() {
        super.initWidget()
        binding.recyclerView.adapter = mAdapter
        binding.tvReaded.click {
            ReadedMessageActivity.actionStart(this)
        }
    }

    override fun initObserver() {
        super.initObserver()
        mViewModel.messages.observe(this) {
            mAdapter.submitList = it.getOrNull()?.datas
        }
    }

    override fun preLoad() {
        super.preLoad()
        mViewModel.listMessage()
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, UnreadMessageActivity::class.java))
        }
    }

}