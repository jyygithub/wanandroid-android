package com.jiangyy.wanandroid.ui.home

import com.jiangyy.wanandroid.databinding.FragmentHomeSearchBinding
import com.jiangyy.wanandroid.ui.article.SearchActivity
import com.koonny.appcompat.BaseFragment
import com.koonny.appcompat.core.click

class HomeSearchFragment : BaseFragment<FragmentHomeSearchBinding>(FragmentHomeSearchBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.tvSearch.click { SearchActivity.actionStart(requireActivity()) }
    }

    companion object {
        fun newInstance() = HomeSearchFragment()
    }

}