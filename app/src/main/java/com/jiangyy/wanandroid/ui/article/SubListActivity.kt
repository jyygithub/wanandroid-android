package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.ui.adapter.SubAdapter

class SubListActivity : BaseLoadActivity<ContentPageListBinding>(), MultipleStateModule {

    private val mAdapter = SubAdapter()

    private val mViewModel by viewModels<SubListViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.toolbar.setTitle("教程")
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { position ->
            mAdapter.currentList[position].let {
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
                mAdapter.submitList(it.getOrNull())
            } else {
                preLoadWithFailure { preLoad() }
            }
        }
    }

    override fun preLoad() {
        mViewModel.listSub()
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SubListActivity::class.java))
        }
    }

}