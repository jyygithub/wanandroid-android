package com.jiangyy.wanandroid.ui.home

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.adapter.ArticleAdapter
import com.jiangyy.wanandroid.base.BaseFragment
import com.jiangyy.wanandroid.databinding.FragmentArticlesBinding
import com.jiangyy.wanandroid.kit.argumentsInt
import com.jiangyy.wanandroid.kit.argumentsString
import com.jiangyy.wanandroid.ktor.ArticleApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber

class ArticlesFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    private val mAdapter = ArticleAdapter()

    private val mInitPage by argumentsInt("initPage", 0)
    private val mPath by argumentsString("path")

    override fun onPrepareValue() {
        super.onPrepareValue()
    }

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.recyclerView.adapter = mAdapter
    }

    override fun onPrepareData() {
        super.onPrepareData()
        lifecycleScope.launch {
            flow { emit(ArticleApi().articles(mPath ?: "", mInitPage ?: 0)) }
                .catch { }.collect {
                    mAdapter.submitList(it.data.datas)
                }
        }
    }

    companion object {
        fun homeProject(): ArticlesFragment {
            return ArticlesFragment().apply {
                arguments = Bundle().apply {
                    putInt("initPage", 0)
                    putString("path", "article/listproject")
                }
            }
        }

        fun homeArticle(): ArticlesFragment {
            return ArticlesFragment().apply {
                arguments = Bundle().apply {
                    putInt("initPage", 0)
                    putString("path", "article/list")
                }
            }
        }

        fun square(): ArticlesFragment {
            return ArticlesFragment().apply {
                arguments = Bundle().apply {
                    putInt("initPage", 0)
                    putString("path", "user_article/list")
                }
            }
        }

        fun wenda(): ArticlesFragment {
            return ArticlesFragment().apply {
                arguments = Bundle().apply {
                    putInt("initPage", 0)
                    putString("path", "wenda/list")
                }
            }
        }

        fun wechat(id: String): ArticlesFragment {
            return ArticlesFragment().apply {
                arguments = Bundle().apply {
                    putInt("initPage", 0)
                    putString("path", "wxarticle/list/${id}")
                }
            }
        }
    }

}