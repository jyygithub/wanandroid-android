package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ActivityMainBinding
import com.jiangyy.wanandroid.ui.main.HomeFragment
import com.jiangyy.wanandroid.ui.main.SearchFragment
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

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment.newInstance()
                    1 -> HomeFragment.newInstance()
                    2 -> HomeFragment.newInstance()
                    3 -> HomeFragment.newInstance()
                    else -> HomeFragment.newInstance()
                }
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> binding.containerView[0].isSelected
                R.id.nav_project -> binding.containerView[1].isSelected
                R.id.nav -> binding.containerView[2].isSelected
                R.id.nav_my -> binding.containerView[3].isSelected
            }

            false
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

}