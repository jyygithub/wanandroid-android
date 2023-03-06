package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koonny.app.core.intentParcelable
import com.jiangyy.wanandroid.databinding.ActivityArticleBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.htmlString
import com.jiangyy.wanandroid.ui.main.dataStore
import com.just.agentweb.AgentWeb
import com.koonny.app.BaseActivity
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
            dataStore.edit { preference ->
                val result = preference[stringPreferencesKey("scan")] ?: Gson().toJson(mutableListOf<Article>())
                val list = Gson().fromJson<MutableList<Article>>(result, object : TypeToken<MutableList<Article>>() {}.type)
                list.add(0, mArticle!!)
                preference[stringPreferencesKey("scan")] = Gson().toJson(list)
            }
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