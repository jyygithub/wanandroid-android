package com.jiangyy.wanandroid.ui.main

import android.app.Activity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.errorToast
import com.jiangyy.core.orZero
import com.jiangyy.dialog.ConfirmDialog
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.data.MyViewModel
import com.jiangyy.wanandroid.data.RefreshScan
import com.jiangyy.wanandroid.databinding.FragmentMyBinding
import com.jiangyy.wanandroid.logic.UserUrl
import com.jiangyy.wanandroid.ui.AboutActivity
import com.jiangyy.wanandroid.ui.adapter.MyAdapter
import com.jiangyy.wanandroid.ui.adapter.MyItem
import com.jiangyy.wanandroid.ui.article.ArticlesActivity
import com.jiangyy.wanandroid.ui.article.SubListActivity
import com.jiangyy.wanandroid.ui.article.TreeActivity
import com.jiangyy.wanandroid.ui.article.WechatActivity
import com.jiangyy.wanandroid.ui.user.CoinHistoryActivity
import com.jiangyy.wanandroid.ui.user.LoginActivity
import com.jiangyy.wanandroid.ui.user.UnreadMessageActivity
import com.jiangyy.wanandroid.ui.user.RankingActivity
import com.jiangyy.wanandroid.utils.DataStoreUtils
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import rxhttp.awaitResult

class MyFragment : BaseLoadFragment<FragmentMyBinding>() {

    private val mAdapter = MyAdapter()

    override fun preInit() {
        super.preInit()
        EventBus.getDefault().register(this)
    }

    override fun initValue() {

    }

    override fun initWidget() {

        binding.toolbar.setOnStartListener {
            UnreadMessageActivity.actionStart(requireActivity())
        }

        binding.toolbar.setOnEndListener {
            ConfirmDialog()
                .bindConfig {
                    title = "提示"
                    content = "确认退出登录"
                }
                .confirm {
                    logout()
                }
                .show(childFragmentManager)
        }
        binding.ivAvatar.setOnClickListener {
            if (DataStoreUtils.logged) {
                // TODO
            } else {
                loginLauncher.launch(LoginActivity.actionIntent(requireActivity()))
            }
        }

        mViewModel.loggerStatus().observe(this) {
            if (it) {
                binding.toolbar.setEnd(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_logout), null)
                binding.tvNickname.text = DataStoreUtils.currentUser.nickname.orEmpty()
            } else {
                binding.toolbar.setEnd(null, null)
                binding.tvNickname.text = "登录 / 注册"
            }
            infoUser()
            getMessageCount()
        }
        mViewModel.myData().observe(this) {
            mAdapter.getItem(0).let { item ->
                item.text = "${it.first}"
                mAdapter.setData(0, item)
            }
            mAdapter.getItem(1).let { item ->
                item.text = "${it.second}"
                mAdapter.setData(1, item)
            }
            mAdapter.getItem(2).let { item ->
                item.text = "${DataStoreUtils.getScanHistory().size}"
                mAdapter.setData(2, item)
            }
        }

        binding.recyclerView.adapter = mAdapter
        mAdapter.setGridSpanSizeLookup { _, _, position ->
            return@setGridSpanSizeLookup mAdapter.getItem(position).row
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> CoinHistoryActivity.actionStart(requireActivity())
                1 -> ArticlesActivity.actionStart(requireActivity(), "collection")
                2 -> ArticlesActivity.actionStart(requireActivity(), "scan")
                4 -> ArticlesActivity.actionStart(requireActivity(), "share")
                7 -> ArticlesActivity.actionStart(requireActivity(), "square")
                8 -> ArticlesActivity.actionStart(requireActivity(), "wenda")
                9 -> TreeActivity.actionStart(requireActivity())
                10 -> WechatActivity.actionStart(requireActivity())
                11 -> SubListActivity.actionStart(requireActivity())
                13 -> RankingActivity.actionStart(requireActivity())
                15 -> AboutActivity.actionStart(requireActivity())
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

    override fun preLoad() {
        mViewModel.loginOrOut(DataStoreUtils.logged)
        infoUser()
        getMessageCount()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onScanUpdated(value: RefreshScan) {
        mAdapter.getItem(2).let { item ->
            item.text = "${DataStoreUtils.getScanHistory().size}"
            mAdapter.setData(2, item)
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            UserUrl.logout()
                .awaitResult {
                    if (it.isSuccess()) {
                        DataStoreUtils.logout()
                        mViewModel.loginOrOut(false)
                    } else {
                        errorToast(it.errorMsg.orEmpty())
                    }
                }
                .onFailure {
                    errorToast("操作失败，请稍后重试")
                }
        }
    }

    private fun infoUser() {
        if (!DataStoreUtils.logged) return
        lifecycleScope.launch {
            UserUrl.infoUser()
                .awaitResult {
                    if (it.data != null) {
                        mViewModel.myData(
                            it.data.userInfo?.coinCount.orZero() to it.data.userInfo?.collectIds?.size.orZero()
                        )
                    }
                }
                .onFailure {

                }
        }
    }

    private fun getMessageCount() {
        if (!DataStoreUtils.logged) return
        lifecycleScope.launch {
            UserUrl.getUnreadMessageCount()
                .awaitResult {
                    if (it.data != null && it.data > 0) {
                        binding.tvMessageCount.text = "${it.data.orZero()}"
                        binding.tvMessageCount.visibility = View.VISIBLE
                    } else {
                        binding.tvMessageCount.visibility = View.GONE
                    }
                }
                .onFailure {
                    binding.tvMessageCount.visibility = View.GONE
                }
        }
    }

    private val mViewModel by viewModels<MyViewModel>()

    private val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            mViewModel.loginOrOut(DataStoreUtils.logged)
        }
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyFragment()
    }

}