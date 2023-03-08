package com.jiangyy.wanandroid.ui.home

import com.jiangyy.wanandroid.databinding.FragmentSearchBinding
import com.jiangyy.wanandroid.ui.article.SearchActivity
import com.koonny.appcompat.BaseFragment
import com.koonny.appcompat.core.click

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.tvSearch.click { SearchActivity.actionStart(requireActivity()) }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

}