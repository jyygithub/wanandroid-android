package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.jiangyy.common.view.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.adapter.TreeAdapter

class TreeActivity : BaseLoadActivity<ContentPageListBinding>(ContentPageListBinding::inflate) {

    override val viewBindStatus: View get() = binding.refreshLayout

    private val mAdapter = TreeAdapter()

    private val mViewModel by viewModels<TreeViewModel>()

    override fun initWidget() {
        super.initWidget()
        binding.toolbar.setTitle("体系")
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mAdapter.getItemViewType(position) == 0) 2 else 1
                }
            }
        }
        binding.recyclerView.adapter = mAdapter
        mAdapter.itemClick { position ->
            mAdapter.getItem(position).let {
                if (it.itemType == 1) {
                    ArticlesActivity.actionStart(this, "tree", it)
                }
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            preLoad()
        }
    }

    override fun initObserver() {
        super.initObserver()
        mViewModel.treeResult.observe(this) {
            binding.refreshLayout.isRefreshing = false
            val result = mutableListOf<Tree>()

            if (it.isSuccess) {
                preLoadSuccess()
                mAdapter.submitList = null
                if (it.getOrNull().isNullOrEmpty()) return@observe
                it.getOrNull()?.forEach { parent ->
                    result.add(parent)
                    parent.children?.forEach { children ->
                        result.add(children)
                    }
                }
                mAdapter.submitList = result
            } else {
                preLoadError(it.exceptionOrNull()?.message.orEmpty())
            }
        }
    }

    override fun preLoad() {
        super.preLoad()
        mViewModel.tree()
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, TreeActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}