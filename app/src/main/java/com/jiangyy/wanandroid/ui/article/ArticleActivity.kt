package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.databinding.ActivityArticleBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.htmlString
import com.jiangyy.wanandroid.utils.localScan
import com.just.agentweb.AgentWeb
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.core.intentParcelable
import kotlinx.coroutines.launch

class ArticleActivity : BaseActivity<ActivityArticleBinding>(ActivityArticleBinding::inflate) {

    private val mArticle by intentParcelable<Article>("article")
    private lateinit var mAgentWeb: AgentWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvTitle.text = mArticle?.title.htmlString
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.frameLayout, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(mArticle?.link.orEmpty().replace("http:", "https:"))

        lifecycleScope.launch {
            localScan(mArticle!!)
        }

    }

    override fun onDestroy() {
        mAgentWeb.destroy()
        super.onDestroy()
    }

    companion object {
        fun actionStart(context: Context, article: Article?) {
            Intent(context, ArticleActivity::class.java).apply {
                this.putExtra("article", article)
                context.startActivity(this)
            }
        }
    }

}