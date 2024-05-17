package com.jiangyy.wanandroid.ui

import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.adapter.MainViewPagerAdapter
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityMainBinding
import com.jiangyy.wanandroid.kit.toast

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), NavigationBarView.OnItemSelectedListener {

    private var mPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    override fun onPrepareValue() {
        super.onPrepareValue()
        doubleBackToExit()
    }

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = MainViewPagerAdapter(this)
        binding.bottomNavigationView.setOnItemSelectedListener(this)
        mPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        }
        mPageChangeCallback?.let {
            binding.viewPager.registerOnPageChangeCallback(it)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> binding.viewPager.currentItem = 0
            R.id.nav_explore -> binding.viewPager.currentItem = 1
            R.id.nav_sub -> binding.viewPager.currentItem = 2
            R.id.nav_smile -> binding.viewPager.currentItem = 3
        }
        return true
    }

    override fun onDestroy() {
        mPageChangeCallback?.let {
            binding.viewPager.unregisterOnPageChangeCallback(it)
        }
        binding.viewPager.adapter = null
        super.onDestroy()
    }

    private fun doubleBackToExit() {
        var doubleBackToExitPressedOnce = false
        onBackPressedDispatcher.addCallback {
            if (doubleBackToExitPressedOnce) {
                finishAndRemoveTask()
            } else {
                doubleBackToExitPressedOnce = true
                toast("再按一次退出程序")
                Handler(Looper.getMainLooper()).postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2000)
            }
        }
    }

}