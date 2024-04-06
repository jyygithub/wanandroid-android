package com.jiangyy.wanandroid.ui.home

import com.jiangyy.wanandroid.base.BaseFragment
import com.jiangyy.wanandroid.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    companion object {
        fun newInstance() = HomeFragment()
    }

}