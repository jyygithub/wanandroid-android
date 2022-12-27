package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.ui.adapter.TreeAdapter

class TreeActivity : BaseLoadActivity<ContentPageListBinding>(), MultipleStateModule {

    private val mAdapter = TreeAdapter()

    private val mViewModel by viewModels<TreeViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.toolbar.setTitle("体系")
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = mAdapter
        mAdapter.setGridSpanSizeLookup { _, viewType, _ ->
            if (viewType == 0) 2 else 1
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItem(position).let {
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
            when (it) {
                is TreeResultSuccess -> {
                    preLoadSuccess()
                    mAdapter.setList(null)
                    if (it.data.isNullOrEmpty()) return@observe
                    for (parent in it.data) {
                        mAdapter.addData(parent)
                        parent.children?.forEach { children ->
                            mAdapter.addData(children)
                        }
                    }
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