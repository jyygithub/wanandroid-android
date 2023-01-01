package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityWechatBinding
import com.jiangyy.wanandroid.entity.Tree

class WechatActivity : BaseLoadActivity<ActivityWechatBinding>(), MultipleStateModule {

    private val mViewModel by viewModels<WechatViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {

    }

    override fun initObserver() {
        mViewModel.wechatResult.observe(this) {
            if (it.isSuccess) {
                preLoadSuccess()
                it.getOrNull()?.forEach { _ ->
                    binding.tabLayout.addTab(binding.tabLayout.newTab())
                }
                initViewPager(it.getOrNull())
            } else {
                preLoadWithFailure { preLoad() }
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