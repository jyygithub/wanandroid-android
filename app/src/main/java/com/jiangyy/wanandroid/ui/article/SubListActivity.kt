package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangyy.common.view.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.ui.adapter.SubAdapter

class SubListActivity : BaseLoadActivity<ContentPageListBinding>(ContentPageListBinding::inflate) {

    override val viewBindStatus: View get() = binding.refreshLayout

    private val mAdapter = SubAdapter()

    private val mViewModel by viewModels<SubListViewModel>()

    override fun initWidget() {
        super.initWidget()
        binding.toolbar.setTitle("教程")
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = mAdapter
        mAdapter.itemClick { position ->
            mAdapter.getItem(position).let {
                ArticlesActivity.actionStart(this, "sub", it)
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            preLoad()
        }
        mViewModel.sublist.observe(this) {
            if (it.isSuccess) {
                preLoadSuccess()
                binding.refreshLayout.isRefreshing = false
                mAdapter.submitList = it.getOrNull()
            } else {
                preLoadError()
            }
        }
    }

    override fun preLoad() {
        super.preLoad()
        mViewModel.listSub()
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SubListActivity::class.java))
        }
    }

}