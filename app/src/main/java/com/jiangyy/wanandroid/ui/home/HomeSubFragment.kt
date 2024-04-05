package com.jiangyy.wanandroid.ui.home

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.data.flowRequest
import com.jiangyy.wanandroid.databinding.FragmentHomeSubBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.home.sub.ArticleInSubFragment
import com.koonny.appcompat.BaseFragment

class HomeSubFragment : BaseFragment<FragmentHomeSubBinding>(FragmentHomeSubBinding::inflate) {

    override fun onPrepareData() {
        super.onPrepareData()
        flowRequest<MutableList<Tree>> {
            request {
                RetrofitHelper.getInstance().create(Api::class.java).listSub()
            }
            response {
                it.getOrNull()?.forEach { _ ->
                    binding.tabLayout.addTab(binding.tabLayout.newTab())
                }
                initViewPager(it.getOrNull())
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
                return ArticleInSubFragment.newInstance(data[position])
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = data[position].name.orEmpty()
        }.attach()
    }

    companion object {
        fun newInstance() = HomeSubFragment()
    }

}