package com.jiangyy.wanandroid.ui.main

import com.jiangyy.wanandroid.databinding.FragmentSearchBinding
import com.koonny.appcompat.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    companion object {
        fun newInstance() = SearchFragment()
    }

}