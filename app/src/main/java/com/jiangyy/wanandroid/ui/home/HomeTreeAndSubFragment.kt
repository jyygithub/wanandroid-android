package com.jiangyy.wanandroid.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.wanandroid.databinding.FragmentHomeTreeAndSubBinding
import com.jiangyy.wanandroid.ui.home.tree2sub.SubFragment
import com.jiangyy.wanandroid.ui.home.tree2sub.TreeFragment
import com.koonny.appcompat.BaseFragment

class HomeTreeAndSubFragment : BaseFragment<FragmentHomeTreeAndSubBinding>(FragmentHomeTreeAndSubBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> TreeFragment.newInstance()
                    1 -> SubFragment.newInstance()
                    else -> TreeFragment.newInstance()
                }
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = arrayOf("体系", "教程")[position]
        }.attach()
    }

    companion object {
        fun newInstance() = HomeTreeAndSubFragment()
    }

}