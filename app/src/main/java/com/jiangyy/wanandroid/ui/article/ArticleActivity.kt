package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.widget.LinearLayout
import com.jiangyy.core.parcelableIntent
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityArticleBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.utils.DataStoreUtils
import com.just.agentweb.AgentWeb

class ArticleActivity : BaseActivity<ActivityArticleBinding>() {

    private val mArticle by parcelableIntent<Article>("article")
    private lateinit var mAgentWeb: AgentWeb

    override fun initValue() {

        DataStoreUtils.scan(mArticle)

    }

    override fun initWidget() {
        binding.tvTitle.text = mArticle?.title.orEmpty()
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.frameLayout, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(mArticle?.link.orEmpty().replace("http:", "https:"))
    }

    companion object {
        fun actionStart(context: Context, article: Article) {
            Intent(context, ArticleActivity::class.java).apply {
                this.putExtra("article", article)
                context.startActivity(this)
            }
        }
    }

}