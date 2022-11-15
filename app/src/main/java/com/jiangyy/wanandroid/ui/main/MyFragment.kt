package com.jiangyy.wanandroid.ui.main

import com.jiangyy.viewbinding.base.BaseFragment
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.FragmentMyBinding
import com.jiangyy.wanandroid.ui.adapter.MyAdapter
import com.jiangyy.wanandroid.ui.adapter.MyItem
import com.jiangyy.wanandroid.ui.article.TreeActivity

class MyFragment : BaseFragment<FragmentMyBinding>() {

    private val mAdapter = MyAdapter()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter
        mAdapter.setGridSpanSizeLookup { _, _, position ->
            return@setGridSpanSizeLookup mAdapter.getItem(position).row
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                9 -> TreeActivity.actionStart(requireActivity())
            }
        }
        mAdapter.setList(
            mutableListOf(
                MyItem(1, 1, "积分", "0"),
                MyItem(1, 1, "收藏", "0"),
                MyItem(1, 1, "最近浏览", "0"),
                MyItem(0, 3),
                MyItem(2, 3, "我的分享", "", R.drawable.shape_my_top, R.drawable.ic_share),
                MyItem(2, 3, "我的待办", "", R.drawable.shape_my_bottom, R.drawable.ic_todo),
                MyItem(0, 3),
                MyItem(2, 3, "广场", "", R.drawable.shape_my_top, R.drawable.ic_square),
                MyItem(2, 3, "每日一问", "", R.drawable.shape_my_center, R.drawable.ic_message),
                MyItem(2, 3, "体系", "", R.drawable.shape_my_center, R.drawable.ic_tree),
                MyItem(2, 3, "公众号", "", R.drawable.shape_my_center, R.drawable.ic_wechat),
                MyItem(2, 3, "教程", "", R.drawable.shape_my_bottom, R.drawable.ic_sub),
                MyItem(0, 3),
                MyItem(2, 3, "排行榜", "", R.drawable.shape_my_round, R.drawable.ic_ranking),
                MyItem(0, 3),
                MyItem(2, 3, "关于", "", R.drawable.shape_my_round, R.drawable.ic_settings),
            )
        )

    }

    companion object {
        @JvmStatic
        fun newInstance() = MyFragment()
    }

}