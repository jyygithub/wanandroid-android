package com.jiangyy.wanandroid.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.jiangyy.app.BaseFragment
import com.jiangyy.app.core.orZero
import com.jiangyy.dialog.ConfirmDialog
import com.jiangyy.wanandroid.AppContext
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.MyAdapter
import com.jiangyy.wanandroid.adapter.MyItem
import com.jiangyy.wanandroid.databinding.FragmentMyBinding
import com.jiangyy.wanandroid.ui.article.ArticlesActivity
import com.jiangyy.wanandroid.ui.article.SubListActivity
import com.jiangyy.wanandroid.ui.article.TreeActivity
import com.jiangyy.wanandroid.ui.article.WechatActivity
import com.jiangyy.wanandroid.ui.user.CoinHistoryActivity
import com.jiangyy.wanandroid.ui.user.LoginActivity
import com.jiangyy.wanandroid.ui.user.RankingActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(AppContext.packageName)

data class CurrentUser(
    var username: String? = null,
    var nickname: String? = null,
)

class HomeMyFragment : BaseFragment<FragmentMyBinding>(FragmentMyBinding::inflate) {

    private val mAdapter = MyAdapter()
    private var mUsername: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            requireContext().dataStore.data.map {
                Gson().fromJson(it[stringPreferencesKey("currentUser")] ?: Gson().toJson(CurrentUser()), CurrentUser::class.java)
            }.collectLatest {
                if (it.username.isNullOrBlank()) {
                    binding.tvNickname.text = "登录/注册"
                } else {
                    mUsername = it.username
                    binding.tvNickname.text = it.nickname
                }
            }
        }
        binding.ivAvatar.setOnClickListener {
            if (mUsername.isNullOrBlank()) {
                LoginActivity.actionStart(requireActivity())
            }
        }
        binding.toolbar.setOnStartListener {
//            UnreadMessageActivity.actionStart(requireActivity())
        }

        binding.toolbar.setOnEndListener {
            ConfirmDialog()
                .title("提示")
                .message("确认退出登录")
                .positive("确定")
                .negative("取消")
                .positiveClick {
                    lifecycleScope.launch {
                        requireContext().dataStore.edit {
                            it[stringPreferencesKey("currentUser")] = Gson().toJson(CurrentUser())
                        }
                    }
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
            when (position) {
                0 -> CoinHistoryActivity.actionStart(requireActivity())
                1 -> ArticlesActivity.actionStart(requireActivity(), "collection")
//                2 -> ArticlesActivity.actionStart(requireActivity(), "scan")
                4 -> ArticlesActivity.actionStart(requireActivity(), "share")
//                5 -> TodosActivity.actionStart(requireActivity())
                7 -> ArticlesActivity.actionStart(requireActivity(), "square")
                8 -> ArticlesActivity.actionStart(requireActivity(), "wenda")
                9 -> TreeActivity.actionStart(requireActivity())
                10 -> WechatActivity.actionStart(requireActivity())
                11 -> SubListActivity.actionStart(requireActivity())
                13 -> RankingActivity.actionStart(requireActivity())
//                15 -> AboutActivity.actionStart(requireActivity())
            }
        }
        mAdapter.submitList(
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

    private fun logout() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeMyFragment()
    }

}