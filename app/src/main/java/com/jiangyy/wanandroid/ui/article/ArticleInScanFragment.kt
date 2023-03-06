package com.jiangyy.wanandroid.ui.article

import android.os.Bundle
import android.view.View
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koonny.app.BaseFragment
import com.jiangyy.wanandroid.adapter.ArticleAdapter
import com.jiangyy.wanandroid.databinding.FragmentArticlesBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.ui.main.dataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
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
            requireContext().dataStore.data.map { preference ->
                val result = preference[stringPreferencesKey("scan")] ?: Gson().toJson(mutableListOf<Article>())
                Gson().fromJson<MutableList<Article>>(result, object : TypeToken<MutableList<Article>>() {}.type)
            }.collectLatest {
                mAdapter.submitList(it)
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleInScanFragment()
    }

}