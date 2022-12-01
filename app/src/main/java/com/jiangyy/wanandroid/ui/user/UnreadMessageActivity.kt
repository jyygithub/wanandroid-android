package com.jiangyy.wanandroid.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.click
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityUnreadMessageBinding
import com.jiangyy.wanandroid.logic.UserUrl
import com.jiangyy.wanandroid.ui.adapter.MessageAdapter
import kotlinx.coroutines.launch
import rxhttp.awaitResult

class UnreadMessageActivity : BaseLoadActivity<ActivityUnreadMessageBinding>() {

    private val mAdapter = MessageAdapter()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        binding.tvReaded.click {
            ReadedMessageActivity.actionStart(this)
        }
    }

    override fun preLoad() {
        lifecycleScope.launch {
            UserUrl.listUnreadMessage(1)
                .awaitResult {
                    mAdapter.setList(it.data?.datas)
                }
                .onFailure {
                }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, UnreadMessageActivity::class.java))
        }
    }

}