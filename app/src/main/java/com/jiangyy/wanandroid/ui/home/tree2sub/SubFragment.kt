package com.jiangyy.wanandroid.ui.home.tree2sub

import androidx.recyclerview.widget.GridLayoutManager
import com.jiangyy.wanandroid.adapter.SubAdapter
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.FragmentSubBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.article.ArticlesActivity
import com.koonny.appcompat.BaseFragment

class SubFragment : BaseFragment<FragmentSubBinding>(FragmentSubBinding::inflate) {

    private val mAdapter = SubAdapter()

    override fun onPrepareData() {
        super.onPrepareData()
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItem(position).let {
                ArticlesActivity.actionStart(requireActivity(), "sub", it)
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
        fun newInstance() = SubFragment()
    }

}