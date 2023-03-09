package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.jiangyy.wanandroid.adapter.TreeAdapter
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.entity.Tree
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.module.StatusModule

class TreeActivity : BaseActivity<ContentPageListBinding>(ContentPageListBinding::inflate), StatusModule {

    private val mAdapter = TreeAdapter()

    override fun viewBindStatus(): View {
        return binding.refreshLayout
    }

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.toolbar.setTitle("体系")
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mAdapter.getItemViewType(position) == 0) 2 else 1
                }
            }
        }
        binding.recyclerView.adapter = mAdapter
        binding.refreshLayout.setOnRefreshListener {
            onPrepareData()
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItem(position)?.let {
                if (it.itemType == 1) {
                    ArticlesActivity.actionStart(this, "tree", it)
                }
            }
        }

    }

    override fun onPrepareData() {
        super.onPrepareData()
        flowRequest<MutableList<Tree>> {
            request {
                startLoading()
                RetrofitHelper.getInstance().create(Api::class.java).tree()
            }
            response {
                finishLoading()
                val result = mutableListOf<Tree>()
                if (it.isSuccess) {
                    it.getOrNull()?.forEach { parent ->
                        result.add(parent)
                        parent.children?.forEach { children ->
                            result.add(children)
                        }
                    }
                    mAdapter.submitList(result)
                } else {

                }
            }
        }
    }

    override fun onStatusRetry() {
        onPrepareData()
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, TreeActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}