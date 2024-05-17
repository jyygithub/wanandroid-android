package com.jiangyy.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jiangyy.wanandroid.ui.home.BooksFragment
import com.jiangyy.wanandroid.ui.home.ExploreFragment
import com.jiangyy.wanandroid.ui.home.HomeFragment
import com.jiangyy.wanandroid.ui.home.MyFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val mFragments: List<Fragment> = listOf(
        HomeFragment(),
        ExploreFragment(),
        BooksFragment(),
        MyFragment(),
    )

    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }
}