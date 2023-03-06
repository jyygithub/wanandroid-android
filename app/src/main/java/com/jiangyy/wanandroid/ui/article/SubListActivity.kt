package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlinapp.app.BaseActivity
import com.jiangyy.wanandroid.adapter.SubAdapter
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.ContentPageListBinding
import com.jiangyy.wanandroid.entity.Tree

class SubListActivity : BaseActivity<ContentPageListBinding>(ContentPageListBinding::inflate) {

    private val mAdapter = SubAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle("教程")
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItem(position).let {
                ArticlesActivity.actionStart(this, "sub", it)
            }
        }

        flowRequest<MutableList<Tree>> {
            request { RetrofitHelper.getInstance().create(Api::class.java).listSub() }
            response {
                mAdapter.submitList(it.getOrNull())
            }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SubListActivity::class.java))
        }
    }

}