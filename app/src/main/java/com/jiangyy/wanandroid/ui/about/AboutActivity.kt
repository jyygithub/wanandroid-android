package com.jiangyy.wanandroid.ui.about

import android.content.Context
import android.content.Intent
import com.jiangyy.wanandroid.adapter.ItemAdapter
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate) {

    private val mAdapter = ItemAdapter()

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.recyclerView.adapter = mAdapter
    }

    override fun onPrepareData() {
        super.onPrepareData()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AboutActivity::class.java))
        }
    }

}