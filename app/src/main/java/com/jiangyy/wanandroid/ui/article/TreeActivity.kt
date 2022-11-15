package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.logic.ArticleUrl
import com.jiangyy.wanandroid.ui.adapter.TreeAdapter
import kotlinx.coroutines.launch
import rxhttp.awaitResult

class TreeActivity : BaseLoadActivity<ContentPageListBinding>(), MultipleStateModule {

    private val mAdapter = TreeAdapter()

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
                    ArticlesActivity.actionStart(this, it)
                }
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            preLoad()
        }
    }

    override fun preLoad() {
        lifecycleScope.launch {
            ArticleUrl.tree()
                .awaitResult {
                    preLoadSuccess()
                    binding.refreshLayout.isRefreshing = false
                    mAdapter.setList(null)
                    if (it.data.isNullOrEmpty()) return@awaitResult
                    for (parent in it.data) {
                        mAdapter.addData(parent)
                        parent.children?.forEach { children ->
                            mAdapter.addData(children)
                        }
                    }
                }
                .onFailure {
                    preLoadWithFailure { preLoad() }
                }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, TreeActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}