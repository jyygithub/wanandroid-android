package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.common.view.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityWechatBinding
import com.jiangyy.wanandroid.entity.Tree

class WechatActivity : BaseLoadActivity<ActivityWechatBinding>(ActivityWechatBinding::inflate) {

    override val viewBindStatus: View get() = binding.viewPager

    private val mViewModel by viewModels<WechatViewModel>()

    override fun initObserver() {
        super.initObserver()
        mViewModel.wechatResult.observe(this) {
            if (it.isSuccess) {
                preLoadSuccess()
                it.getOrNull()?.forEach { _ ->
                    binding.tabLayout.addTab(binding.tabLayout.newTab())
                }
                initViewPager(it.getOrNull())
            } else {
                preLoadError()
            }
        }
    }

    private fun initViewPager(data: MutableList<Tree>?) {
        if (data.isNullOrEmpty()) return
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {

            override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
                super.onAttachedToRecyclerView(recyclerView)
                recyclerView.setItemViewCacheSize(data.size)
            }

            override fun getItemCount(): Int {
                return data.size
            }

            override fun createFragment(position: Int): Fragment {
                return ArticleInWechatFragment.newInstance(data[position].id.orEmpty())
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> tab.text = data[position].name.orEmpty() }.attach()
    }

    override fun preLoad() {
        super.preLoad()
        mViewModel.listWechat()
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, WechatActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}