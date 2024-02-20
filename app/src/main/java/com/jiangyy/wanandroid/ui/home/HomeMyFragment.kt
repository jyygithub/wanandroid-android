package com.jiangyy.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.MyAdapter
import com.jiangyy.wanandroid.adapter.MyItem
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.FragmentHomeMyBinding
import com.jiangyy.wanandroid.entity.UserInfo
import com.jiangyy.wanandroid.ui.AboutActivity
import com.jiangyy.wanandroid.ui.article.ArticlesActivity
import com.jiangyy.wanandroid.ui.article.TreeActivity
import com.jiangyy.wanandroid.ui.coin.CoinHistoryActivity
import com.jiangyy.wanandroid.ui.coin.RankingActivity
import com.jiangyy.wanandroid.ui.user.LoginActivity
import com.jiangyy.wanandroid.utils.*
import com.koonny.appcompat.BaseFragment
import com.koonny.appcompat.core.orZero
import com.koonny.appcompat.core.toast
import com.koonny.dialog.ConfirmDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeMyFragment : BaseFragment<FragmentHomeMyBinding>(FragmentHomeMyBinding::inflate) {

    private val mAdapter = MyAdapter()
    private var mUsername: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            requireContext().localUser().collectLatest {
                if (it.username.isNullOrBlank()) {
                    binding.tvNickname.text = "登录/注册"
                    binding.toolbar.setEnd(null, null)
                    mAdapter.getItem(0)?.let { item ->
                        item.text = "0"
                        mAdapter[0] = item
                    }
                    mAdapter.getItem(1)?.let { item ->
                        item.text = "0"
                        mAdapter[1] = item
                    }
                } else {
                    mUsername = it.username
                    binding.toolbar.setEnd(ContextCompat.getDrawable(requireContext(), R.drawable.ic_logout), null)
                    binding.tvNickname.text = it.nickname
                    infoUser()
                }
            }
        }
        binding.ivAvatar.setOnClickListener {
            if (mUsername.isNullOrBlank()) {
                LoginActivity.actionStart(requireActivity())
            }
        }
        binding.toolbar.setOnEndListener {
            ConfirmDialog()
                .title("提示")
                .message("确认退出登录")
                .positive("确定")
                .negative("取消")
                .positiveClick {
                    logout()
                }
                .show(childFragmentManager)
        }
        val layoutManager = GridLayoutManager(requireActivity(), 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return mAdapter.getItem(position)?.row.orZero()
            }
        }
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            when (mAdapter.getItem(position)?.title) {
                "积分" -> CoinHistoryActivity.actionStart(requireActivity())
                "收藏" -> ArticlesActivity.actionStart(requireActivity(), "collection")
                "最近浏览" -> ArticlesActivity.actionStart(requireActivity(), "scan")
                "我的分享" -> ArticlesActivity.actionStart(requireActivity(), "share")
                "体系" -> TreeActivity.actionStart(requireActivity())
                "排行榜" -> RankingActivity.actionStart(requireActivity())
                "关于" -> AboutActivity.actionStart(requireActivity())
            }
        }
        mAdapter.submitList(
            mutableListOf(
                MyItem(1, 1, "积分", "0"),
                MyItem(1, 1, "收藏", "0"),
                MyItem(1, 1, "最近浏览", "0"),
                MyItem(0, 3),
                MyItem(2, 3, "我的分享", "", R.drawable.shape_my_round, R.drawable.ic_share),
                MyItem(0, 3),
                MyItem(2, 3, "体系", "", R.drawable.shape_my_round, R.drawable.ic_tree_normal),
                MyItem(0, 3),
                MyItem(2, 3, "排行榜", "", R.drawable.shape_my_round, R.drawable.ic_ranking),
                MyItem(0, 3),
                MyItem(2, 3, "关于", "", R.drawable.shape_my_round, R.drawable.ic_settings),
            )
        )
    }

    private fun logout() {
        flowRequest {
            request { RetrofitHelper.getInstance().create(Api::class.java).logout() }
            response {
                if (it.isSuccess) {
                    lifecycleScope.launch {
                        requireContext().localLogout()
                    }
                } else {
                    toast(it.exceptionOrNull()?.message.orEmpty())
                }
            }
        }
    }

    private fun infoUser() {
        flowRequest<UserInfo> {
            request { RetrofitHelper.getInstance().create(Api::class.java).infoUser() }
            response {
                if (it.getOrNull() == null) return@response
                mAdapter.getItem(0)?.let { item ->
                    item.text = "${it.getOrNull()!!.userInfo?.coinCount.orZero()}"
                    mAdapter[0] = item
                }
                mAdapter.getItem(1)?.let { item ->
                    item.text = "${it.getOrNull()!!.collectArticleInfo?.count.orZero()}"
                    mAdapter[1] = item
                }

            }
        }

        lifecycleScope.launch {
            requireContext().localScan().collectLatest {
                mAdapter.getItem(2)?.let { item ->
                    item.text = "${it.size}"
                    mAdapter[2] = item
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeMyFragment()
    }

}