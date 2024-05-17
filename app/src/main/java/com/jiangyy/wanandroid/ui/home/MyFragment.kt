package com.jiangyy.wanandroid.ui.home

import com.chad.library.adapter4.QuickAdapterHelper
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.MyBottomItem
import com.jiangyy.wanandroid.adapter.MyContentAdapter
import com.jiangyy.wanandroid.adapter.MyHeadAdapter
import com.jiangyy.wanandroid.adapter.MyRoundItem
import com.jiangyy.wanandroid.adapter.MyTopItem
import com.jiangyy.wanandroid.base.BaseFragment
import com.jiangyy.wanandroid.databinding.FragmentMyBinding

class MyFragment : BaseFragment<FragmentMyBinding>(FragmentMyBinding::inflate) {

    private val mMyHeadAdapter = MyHeadAdapter()
    private val mMyContentAdapter = MyContentAdapter()

    private lateinit var mHelper: QuickAdapterHelper

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        mHelper = QuickAdapterHelper.Builder(mMyContentAdapter).build()
        mHelper.addBeforeAdapter(mMyHeadAdapter)
        binding.recyclerView.adapter = mHelper.adapter

        mMyHeadAdapter.item = ""

        mMyContentAdapter.submitList(
            listOf(
                MyTopItem(R.drawable.ic_home, "我的分享", ""),
                MyBottomItem(R.drawable.ic_home, "我的收藏", ""),
                MyRoundItem(R.drawable.ic_home, "知识体系", ""),
                MyTopItem(R.drawable.ic_home, "我的积分", "0"),
                MyBottomItem(R.drawable.ic_home, "排行榜", ""),
                MyTopItem(R.drawable.ic_home, "当前版本", "v1.0.0"),
                MyBottomItem(R.drawable.ic_home, "关于", ""),
            )
        )
    }

}