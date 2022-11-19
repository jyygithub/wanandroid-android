package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.logic.ArticleUrl
import com.jiangyy.wanandroid.ui.adapter.SubAdapter
import kotlinx.coroutines.launch
import rxhttp.awaitResult

class SubListActivity : BaseLoadActivity<ContentPageListBinding>(), MultipleStateModule {

    private val mAdapter = SubAdapter()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.toolbar.setTitle("教程")
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItem(position).let {
                ArticlesActivity.actionStart(this, "sub", it)
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            preLoad()
        }
    }

    override fun preLoad() {
        lifecycleScope.launch {
            ArticleUrl.listSub()
                .awaitResult {
                    preLoadSuccess()
                    binding.refreshLayout.isRefreshing = false
                    mAdapter.setList(it.data)
                }
                .onFailure {
                    preLoadWithFailure { preLoad() }
                }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SubListActivity::class.java))
        }
    }

}