package com.jiangyy.wanandroid.ui.todo

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jiangyy.viewbinding.base.BaseLoadActivity
import com.jiangyy.wanandroid.databinding.ActivityTodosBinding

class TodosActivity : BaseLoadActivity<ActivityTodosBinding>() {

    override fun initValue() {

    }

    override fun initWidget() {

        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return TodosFragment.newInstance(position)
            }
        }

    }

    override fun preLoad() {

    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, TodosActivity::class.java))
        }
    }

}