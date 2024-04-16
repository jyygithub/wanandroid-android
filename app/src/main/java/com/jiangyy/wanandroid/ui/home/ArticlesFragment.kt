package com.jiangyy.wanandroid.ui.home

import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.adapter.ArticleAdapter
import com.jiangyy.wanandroid.base.BaseFragment
import com.jiangyy.wanandroid.databinding.FragmentArticlesBinding
import com.jiangyy.wanandroid.ktor.ArticleApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ArticlesFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    private val mAdapter = ArticleAdapter()

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.recyclerView.adapter = mAdapter
    }

    override fun onPrepareData() {
        super.onPrepareData()
        lifecycleScope.launch {
            flow {
                emit(ArticleApi().pageHomeArticle(1))
            }.catch { }.collect {
                mAdapter.submitList(it.data.datas)
            }
        }
    }

}