package com.jiangyy.wanandroid.ui.article

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.koonny.appcompat.BaseFragment
import com.jiangyy.wanandroid.adapter.ArticleAdapter
import com.jiangyy.wanandroid.databinding.FragmentArticlesBinding
import com.jiangyy.wanandroid.utils.localScan
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 浏览记录
 */
class ArticleInScanFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    private val mAdapter = ArticleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            ArticleActivity.actionStart(requireActivity(), mAdapter.getItem(position))
        }

        lifecycleScope.launch {
            requireContext().localScan().collectLatest {
                mAdapter.submitList(it)
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInScanFragment()
    }

}