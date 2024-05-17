package com.jiangyy.wanandroid.ui.home

import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.base.BaseFragment
import com.jiangyy.wanandroid.databinding.FragmentExploreBinding

class ExploreFragment : BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        childFragmentManager.beginTransaction()
            .add(R.id.containerView,ArticlesFragment.homeArticle())
            .commit()
    }

}