package com.jiangyy.wanandroid.ui.home

import android.content.Context
import android.content.Intent
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityArticleBinding

class ArticleActivity : BaseActivity<ActivityArticleBinding>(ActivityArticleBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.webView.loadUrl(intent.getStringExtra("url") ?: "")
    }

    companion object {
        fun start(context: Context, url: String?) {
            context.startActivity(Intent(context, ArticleActivity::class.java).apply {
                putExtra("url", url)
            })
        }
    }

}