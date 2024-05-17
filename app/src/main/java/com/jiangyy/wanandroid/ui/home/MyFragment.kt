package com.jiangyy.wanandroid.ui.home

import android.view.View
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.MyBottomItem
import com.jiangyy.wanandroid.adapter.MyContentAdapter
import com.jiangyy.wanandroid.adapter.MyHeadAdapter
import com.jiangyy.wanandroid.adapter.MyItem
import com.jiangyy.wanandroid.adapter.MyRoundItem
import com.jiangyy.wanandroid.adapter.MyTopItem
import com.jiangyy.wanandroid.base.BaseFragment
import com.jiangyy.wanandroid.databinding.FragmentMyBinding
import com.jiangyy.wanandroid.kit.toast
import com.jiangyy.wanandroid.ui.RankingActivity

class MyFragment : BaseFragment<FragmentMyBinding>(FragmentMyBinding::inflate), BaseQuickAdapter.OnItemClickListener<MyItem> {

    private val mMyHeadAdapter = MyHeadAdapter()
    private val mMyContentAdapter = MyContentAdapter()

    private lateinit var mHelper: QuickAdapterHelper

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        mHelper = QuickAdapterHelper.Builder(mMyContentAdapter).build()
        mHelper.addBeforeAdapter(mMyHeadAdapter)
        binding.recyclerView.adapter = mHelper.adapter

        mMyHeadAdapter.item = ""
        mMyContentAdapter.setOnItemClickListener(this)
        mMyContentAdapter.submitList(
            listOf(
                MyTopItem(R.drawable.ic_public_share, "我的分享", ""),
                MyBottomItem(R.drawable.ic_public_favor, "我的收藏", ""),
                MyRoundItem(R.drawable.ic_public_storage, "知识体系", ""),
                MyTopItem(R.drawable.ic_public_highlights, "我的积分", "0"),
                MyBottomItem(R.drawable.ic_desktop_servicewidgets, "排行榜", ""),
                MyTopItem(R.drawable.ic_public_upgrade, "当前版本", "v1.0.0"),
                MyBottomItem(R.drawable.ic_gallery_cloud_synchronization, "开源地址", "Github"),
            )
        )
    }

    override fun onClick(adapter: BaseQuickAdapter<MyItem, *>, view: View, position: Int) {
        when (position) {
            0 -> {
                toast("分享")
            }

            1 -> {
                toast("收藏")
            }

            2 -> {
                toast("知识体系")
            }

            3 -> {
                toast("积分")
            }

            4 -> { // 排行榜
                RankingActivity.start(requireActivity())
            }

            5 -> {
                toast("版本")
            }

            6 -> {
                toast("开源地址")
            }
        }
    }
}