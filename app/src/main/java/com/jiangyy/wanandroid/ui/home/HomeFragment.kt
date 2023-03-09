package com.jiangyy.wanandroid.ui.home

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.FragmentHomeBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.home.home.ArticleInSquareFragment
import com.jiangyy.wanandroid.ui.home.home.ArticleInWechatFragment
import com.jiangyy.wanandroid.ui.home.home.ArticleInWendaFragment
import com.jiangyy.wanandroid.ui.home.home.ArticleInProjectsFragment
import com.koonny.appcompat.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onPrepareData() {
        super.onPrepareData()
        listWeChat()
    }

    private fun initViewPager(data: MutableList<Tree>?) {
        if (data.isNullOrEmpty()) return
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {

            override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
                super.onAttachedToRecyclerView(recyclerView)
                recyclerView.setItemViewCacheSize(data.size + 3)
            }

            override fun getItemCount(): Int {
                return data.size + 3
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> ArticleInProjectsFragment.newInstance()
                    1 -> ArticleInSquareFragment.newInstance()
                    2 -> ArticleInWendaFragment.newInstance()
                    else -> ArticleInWechatFragment.newInstance(data[position - 3].id.orEmpty())
                }
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "项目"
                1 -> "广场"
                2 -> "问答"
                else -> data[position - 3].name.orEmpty()
            }
        }.attach()
    }

    private fun listWeChat() {
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

    companion object {
        fun newInstance() = HomeFragment()
    }

}