package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.adapter.TreeAdapter

class WechatActivity : BaseLoadActivity<ContentPageListBinding>(), MultipleStateModule {

    private val mAdapter = TreeAdapter()

    private val mViewModel by viewModels<WechatViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.toolbar.setTitle("公众号")
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { position ->
            mAdapter.currentList[position].let {
                if (it.itemType == 1) {
                    ArticlesActivity.actionStart(this, "wechat", it)
                }
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            preLoad()
        }
        mViewModel.wechatResult.observe(this) {
            val result = mutableListOf<Tree>()
            if (it.isSuccess) {
                preLoadSuccess()
                binding.refreshLayout.isRefreshing = false
                mAdapter.submitList(null)
                if (it.getOrNull().isNullOrEmpty()) return@observe
                it.getOrNull()?.forEach { parent ->
                    result.add(parent)
                    parent.children?.forEach { children ->
                        result.add(children)
                    }
                }
                mAdapter.submitList(result)
            } else {
                preLoadWithFailure { preLoad() }
            }
        }
    }

    override fun preLoad() {
        mViewModel.listWechat()
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, WechatActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}