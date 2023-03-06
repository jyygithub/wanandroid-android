package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.core.toast
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ActivityMainBinding
import com.jiangyy.wanandroid.ui.main.HomeArticlesFragment
import com.jiangyy.wanandroid.ui.main.HomeProjectsFragment
import com.jiangyy.wanandroid.ui.main.HomeMyFragment
import kotlin.system.exitProcess

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val mPnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.bottomNavigationView.menu.getItem(position).isChecked = true
            binding.toolbar.isVisible = position != 2
            binding.toolbar.setTitle(arrayOf("首页文章", "首页项目", "")[position])
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var pressedTime: Long = 0
        onBackPressedDispatcher.addCallback(this) {
            val nowTime = System.currentTimeMillis()
            when {
                (nowTime - pressedTime) > 2000 -> {
                    toast("再按一次退出程序")
                    pressedTime = nowTime
                }

                else -> {
                    finish()
                    exitProcess(0)
                }
            }
        }
        val fragments = arrayOf(HomeArticlesFragment.newInstance(), HomeProjectsFragment.newInstance(), HomeMyFragment.newInstance())
        val itemTabs = intArrayOf(R.id.nav_article, R.id.nav_project, R.id.nav_my)
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {

            override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
                super.onAttachedToRecyclerView(recyclerView)
                recyclerView.setItemViewCacheSize(fragments.size)
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

            override fun getItemCount(): Int {
                return fragments.size
            }
        }
        binding.viewPager.registerOnPageChangeCallback(mPnPageChangeCallback)
        binding.bottomNavigationView.setOnItemSelectedListener {
            binding.viewPager.setCurrentItem(itemTabs.indexOf(it.itemId), true)
            true
        }
    }

    override fun onDestroy() {
        binding.viewPager.unregisterOnPageChangeCallback(mPnPageChangeCallback)
        super.onDestroy()
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

}