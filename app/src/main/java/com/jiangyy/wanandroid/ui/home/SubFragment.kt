package com.jiangyy.wanandroid.ui.home

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangyy.wanandroid.adapter.SubAdapter
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.FragmentSubBinding
import com.jiangyy.wanandroid.ui.article.ArticlesActivity
import com.koonny.appcompat.BaseFragment
import com.koonny.appcompat.module.StatusModule

class SubFragment : BaseFragment<FragmentSubBinding>(FragmentSubBinding::inflate), StatusModule {

    private val mAdapter = SubAdapter()

    override fun onStatusRetry(exception: Exception) {
        onPrepareData()
    }

    override fun viewBindStatus(): View {
        return binding.recyclerView
    }

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.getItem(position).let {
                ArticlesActivity.actionStart(requireActivity(), "sub", it)
            }
        }
    }

    override fun onPrepareData() {
        super.onPrepareData()
        flowRequest {
            request {
                startLoading()
                RetrofitHelper.getInstance().create(Api::class.java).listSub()
            }
            response {
                finishLoading()
                mAdapter.submitList(it.getOrNull())
            }
        }
    }

    companion object {
        fun newInstance() = SubFragment()
    }

}