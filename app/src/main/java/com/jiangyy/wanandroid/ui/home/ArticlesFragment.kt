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

class ArticlesFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    private val mAdapter = ArticleAdapter()

    private val mInitPage by argumentsInt("initPage", 0)
    private val mPath by argumentsString("path")
    private val mQueryKey by argumentsString("queryKey")
    private val mQueryValue by argumentsString("queryValue")

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            ArticleActivity.start(requireActivity(), adapter.getItem(position)?.link)
        }
    }

    override fun onPrepareData() {
        super.onPrepareData()
        lifecycleScope.launch {
            flow {
                emit(
                    if (mQueryKey != null && mQueryValue != null) {
                        ArticleApi().articles(mPath ?: "", mInitPage ?: 0, mQueryKey!! to mQueryValue!!)
                    } else {
                        ArticleApi().articles(mPath ?: "", mInitPage ?: 0)
                    }
                )
            }.catch { }.collect {
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

        fun sub(cid: String?): ArticlesFragment {
            return ArticlesFragment().apply {
                arguments = Bundle().apply {
                    putInt("initPage", 0)
                    putString("path", "article/list")
                    putString("queryKey", "cid")
                    putString("queryValue", cid)
                }
            }
        }

    }

}