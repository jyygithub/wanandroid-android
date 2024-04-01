package com.jiangyy.wanandroid.ui

import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), NavigationBarView.OnItemSelectedListener {

    private var mPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    private var doubleBackToExitPressedOnce = false

    override fun onPrepareValue() {
        super.onPrepareValue()
        onBackPressedDispatcher.addCallback {
            if (doubleBackToExitPressedOnce) {
                finishAndRemoveTask()
            } else {
                doubleBackToExitPressedOnce = true
                toast(getString(R.string.press_back_again_to_exit))
                Handler(Looper.getMainLooper()).postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2000)
            }
        }
    }

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.adapter = MainViewPagerAdapter(this)

        mPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        }
        mPageChangeCallback?.let {
            binding.viewPager.registerOnPageChangeCallback(it)
        }
        binding.bottomNavigationView.setOnItemSelectedListener(this)
        mViewModel.version.observe(this) {
            if (it.versionCode != appVersion.first) {
                mApkDownloadManager.start(it)
            } else if (getSharedPreferences("isnooker", MODE_PRIVATE).getBoolean("app_beta", false)) {
                if (it.versionName != appVersion.second) {
                    mApkDownloadManager.start(it)
                }
            }
        }
        mViewModel.betaVersion.observe(this) {
            if (it.versionCode == appVersion.first && it.versionName != appVersion.second) {
                mApkDownloadManager.start(it)
            }
        }
    }

    override fun onPrepareData() {
        super.onPrepareData()
        mViewModel.update()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_upcoming -> binding.viewPager.currentItem = 0
            R.id.nav_calendar -> binding.viewPager.currentItem = 1
            R.id.nav_rankings -> binding.viewPager.currentItem = 2
            R.id.nav_news -> binding.viewPager.currentItem = 3
            R.id.nav_my -> binding.viewPager.currentItem = 4
        }
        return true
    }

    override fun onDestroy() {
        mApkDownloadManager.onDestroy()
        mPageChangeCallback?.let {
            binding.viewPager.unregisterOnPageChangeCallback(it)
        }
        binding.viewPager.adapter = null
        super.onDestroy()
    }

}