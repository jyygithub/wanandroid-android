package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.jiangyy.core.toast
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ActivityMainBinding
import com.jiangyy.wanandroid.ui.main.ArticlesFragment
import com.jiangyy.wanandroid.ui.main.MyFragment
import com.jiangyy.wanandroid.ui.main.ProjectsFragment
import kotlin.system.exitProcess

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var mPressedTime: Long = 0

    private val mPnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.bottomNavigationView.menu.getItem(position).isChecked = true
        }
    }

    override fun initValue() {
        onBackPressedDispatcher.addCallback(this) {
            val nowTime = System.currentTimeMillis()
            when {
                (nowTime - mPressedTime) > 2000 -> {
                    toast("再按一次退出程序")
                    mPressedTime = nowTime
                }
                else -> {
                    finish()
                    exitProcess(0)
                }
            }
        }
    }

    override fun initWidget() {



        val fragments = arrayOf(ArticlesFragment.newInstance(), ProjectsFragment.newInstance(), MyFragment.newInstance())
        val itemTabs = intArrayOf(R.id.nav_article, R.id.nav_project, R.id.nav_my)
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
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