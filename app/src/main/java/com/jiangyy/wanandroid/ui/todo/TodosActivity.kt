package com.jiangyy.wanandroid.ui.todo

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jiangyy.common.utils.click
import com.jiangyy.common.view.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityTodosBinding

class TodosActivity : BaseActivity<ActivityTodosBinding>(ActivityTodosBinding::inflate) {

    override fun initWidget() {
        super.initWidget()
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

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, TodosActivity::class.java))
        }
    }

}