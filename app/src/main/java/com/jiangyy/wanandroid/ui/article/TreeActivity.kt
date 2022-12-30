package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.adapter.TreeAdapter

class TreeActivity : BaseLoadActivity<ContentPageListBinding>(), MultipleStateModule {

    private val mAdapter = TreeAdapter()

    private val mViewModel by viewModels<TreeViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.toolbar.setTitle("体系")
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mAdapter.getItemViewType(position) == 0) 2 else 1
                }
            }
        }
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { position ->
            mAdapter.currentList[position].let {
                if (it.itemType == 1) {
                    ArticlesActivity.actionStart(this, "tree", it)
                }
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            preLoad()
        }
        mViewModel.treeResult().observe(this) {
            binding.refreshLayout.isRefreshing = false
            val result = mutableListOf<Tree>()
            when (it) {
                is TreeResultSuccess -> {
                    preLoadSuccess()
                    mAdapter.submitList(null)
                    if (it.data.isNullOrEmpty()) return@observe
                    for (parent in it.data) {
                        result.add(parent)
                        parent.children?.forEach { children ->
                            result.add(children)
                        }
                    }
                    mAdapter.submitList(result)
                }

                is TreeResultError -> {
                    preLoadWithFailure(it.error.message.orEmpty()) { preLoad() }
                }
            }


        }
    }

    override fun preLoad() {
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