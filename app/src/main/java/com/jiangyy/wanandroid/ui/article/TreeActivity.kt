package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.koonny.appcompat.BaseActivity
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.adapter.TreeAdapter
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest

class TreeActivity : BaseActivity<ContentPageListBinding>(ContentPageListBinding::inflate) {

    private val mAdapter = TreeAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        }
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItem(position)?.let {
                if (it.itemType == 1) {
                    ArticlesActivity.actionStart(this, "tree", it)
                }
            }
        }
        flowRequest<MutableList<Tree>> {
            request { RetrofitHelper.getInstance().create(Api::class.java).tree() }
            response {
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

    companion object {
        fun actionStart(context: Context) {
            Intent(context, TreeActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}