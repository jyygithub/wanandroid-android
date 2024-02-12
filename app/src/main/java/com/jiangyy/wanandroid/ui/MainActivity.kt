package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.navigation.NavigationBarView
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityMainBinding
import com.jiangyy.wanandroid.kit.toast
import com.jiangyy.wanandroid.ui.home.HomeFragment
import com.jiangyy.wanandroid.ui.home.HomeMyFragment
import com.jiangyy.wanandroid.ui.home.HomeSearchFragment
import com.jiangyy.wanandroid.ui.home.HomeSubFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), NavigationBarView.OnItemSelectedListener {

    private var doubleBackToExitPressedOnce = false

    override fun onPrepareValue() {
        super.onPrepareValue()
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
                    1 -> HomeSearchFragment.newInstance()
                    2 -> HomeSubFragment.newInstance()
                    3 -> HomeMyFragment.newInstance()
                    else -> HomeFragment.newInstance()
                }
            }
        }
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val recyclerView = binding.containerView.getChildAt(0) as RecyclerView
        when (p0.itemId) {
            R.id.nav_home -> recyclerView.scrollToPosition(0)
            R.id.nav_explore -> recyclerView.scrollToPosition(1)
            R.id.nav_sub -> recyclerView.scrollToPosition(2)
            R.id.nav_smile -> recyclerView.scrollToPosition(3)
        }
        return true
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

}