package com.jiangyy.wanandroid.ui.main

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.wanandroid.databinding.FragmentHomeBinding
import com.jiangyy.wanandroid.ui.article.ArticleInSquareFragment
import com.jiangyy.wanandroid.ui.article.ArticleInWendaFragment
import com.koonny.appcompat.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3

            override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
                super.onAttachedToRecyclerView(recyclerView)
                recyclerView.setItemViewCacheSize(3)
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomeProjectsFragment.newInstance()
                    1 -> ArticleInSquareFragment.newInstance()
                    2 -> ArticleInWendaFragment.newInstance()
                    else -> HomeProjectsFragment.newInstance()
                }
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = arrayOf("最新项目", "广场", "每日一问")[position]
        }.attach()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}