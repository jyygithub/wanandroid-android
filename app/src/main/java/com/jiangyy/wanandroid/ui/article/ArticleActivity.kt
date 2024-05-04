package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.widget.LinearLayout
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityArticleBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.kit.intentParcelable
import com.just.agentweb.AgentWeb

class ArticleActivity : BaseActivity<ActivityArticleBinding>(ActivityArticleBinding::inflate) {

    private val mArticle by intentParcelable<Article>("article")
    private lateinit var mAgentWeb: AgentWeb

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.frameLayout, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(mArticle?.link.orEmpty().replace("http:", "https:"))
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