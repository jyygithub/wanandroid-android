package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ActivityMainBinding
import com.jiangyy.wanandroid.ui.home.HomeFragment
import com.jiangyy.wanandroid.ui.home.HomeMyFragment
import com.jiangyy.wanandroid.ui.home.HomeSubFragment
import com.jiangyy.wanandroid.ui.home.SearchFragment
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.core.toast
import kotlin.system.exitProcess

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var pressedTime: Long = 0
        onBackPressedDispatcher.addCallback(this) {
            val nowTime = System.currentTimeMillis()
            if (nowTime - pressedTime > 2000) {
                toast("再按一次退出程序")
                pressedTime = nowTime
            } else {
                finish()
                exitProcess(0)
            }
        }
    }

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.containerView.isUserInputEnabled = false
        binding.containerView.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 4

            override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
                super.onAttachedToRecyclerView(recyclerView)
                recyclerView.setItemViewCacheSize(4)
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment.newInstance()
                    1 -> SearchFragment.newInstance()
                    2 -> HomeSubFragment.newInstance()
                    3 -> HomeMyFragment.newInstance()
                    else -> HomeFragment.newInstance()
                }
            }
        }
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> (binding.containerView.getChildAt(0) as RecyclerView).scrollToPosition(0)
                R.id.nav_explore -> (binding.containerView.getChildAt(0) as RecyclerView).scrollToPosition(1)
                R.id.nav_tree -> (binding.containerView.getChildAt(0) as RecyclerView).scrollToPosition(2)
                R.id.nav_smile -> (binding.containerView.getChildAt(0) as RecyclerView).scrollToPosition(3)
            }
            true
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

}