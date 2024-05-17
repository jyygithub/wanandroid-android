package com.jiangyy.wanandroid.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.wanandroid.adapter.HomeViewPagerAdapter
import com.jiangyy.wanandroid.base.BaseFragment
import com.jiangyy.wanandroid.databinding.FragmentHomeBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ktor.ArticleApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
    }

    override fun onPrepareData() {
        super.onPrepareData()
        lifecycleScope.launch {
            flow { emit(ArticleApi().listWechat()) }
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
                recyclerView.setItemViewCacheSize(data.size + 3)
            }

            override fun getItemCount(): Int {
                return data.size + 3
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> ArticlesFragment.homeProject()
                    1 -> ArticlesFragment.square()
                    2 -> ArticlesFragment.wenda()
                    else -> ArticlesFragment.wechat(data[position - 3].id.orEmpty())
                }
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "项目"
                1 -> "广场"
                2 -> "问答"
                else -> data[position - 3].name.orEmpty()
            }
        }.attach()
    }

}