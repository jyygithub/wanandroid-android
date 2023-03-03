package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.app.BaseActivity
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.ActivityWechatBinding
import com.jiangyy.wanandroid.entity.Tree

class WechatActivity : BaseActivity<ActivityWechatBinding>(ActivityWechatBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flowRequest<MutableList<Tree>> {
            request { RetrofitHelper.getInstance().create(Api::class.java).listWechat() }
            response {
                it.getOrNull()?.forEach { _ ->
                    binding.tabLayout.addTab(binding.tabLayout.newTab())
                }
                initViewPager(it.getOrNull())
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

    companion object {
        fun actionStart(context: Context) {
            Intent(context, WechatActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}