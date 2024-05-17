package com.jiangyy.wanandroid.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.wanandroid.base.BaseFragment
import com.jiangyy.wanandroid.databinding.FragmentBooksBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ktor.ArticleApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class BooksFragment : BaseFragment<FragmentBooksBinding>(FragmentBooksBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
    }

    override fun onPrepareData() {
        super.onPrepareData()
        lifecycleScope.launch {
            flow { emit(ArticleApi().listSub()) }
                .catch { }.collect {
                    it.data.forEach { item ->
                        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(item.name))
                    }
                    initViewPager(it.data)
                }
        }
    }

    private fun initViewPager(data: MutableList<Tree>?) {
        if (data.isNullOrEmpty()) return
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {

            override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
                super.onAttachedToRecyclerView(recyclerView)
                recyclerView.setItemViewCacheSize(data.size)
            }

            override fun getItemCount(): Int {
                return data.size
            }

            override fun createFragment(position: Int): Fragment {
                return ArticlesFragment.sub(data[position].id)
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text =data[position].name.orEmpty()
        }.attach()
    }

}