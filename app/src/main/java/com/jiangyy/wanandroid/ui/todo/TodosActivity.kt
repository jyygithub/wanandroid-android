package com.jiangyy.wanandroid.ui.todo

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.core.click
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityTodosBinding

class TodosActivity : BaseLoadActivity<ActivityTodosBinding>() {

    override fun initValue() {

    }

    override fun initWidget() {
        binding.button.click {
            AddTodoActivity.actionStart(this)
        }
        val titles = arrayOf("待完成", "已完成")
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return titles.size
            }

            override fun createFragment(position: Int): Fragment {
                return TodosFragment.newInstance(position)
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> tab.text = titles[position] }.attach()

    }

    override fun preLoad() {

    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, TodosActivity::class.java))
        }
    }

}