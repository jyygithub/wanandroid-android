package com.jiangyy.wanandroid.ui.article

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityArticleBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.kit.intentParcelable
import com.jiangyy.wanandroid.utils.localScan
import com.just.agentweb.AgentWeb
import com.jiangyy.wanandroid.kit.toast
import kotlinx.coroutines.launch

class ArticleActivity : BaseActivity<ActivityArticleBinding>(ActivityArticleBinding::inflate), Toolbar.OnMenuItemClickListener {

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
        binding.toolbar.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_collect -> Unit
            R.id.action_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, mArticle?.link)
                    type = "text/plain"
                }
//                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(sendIntent)

            }
            R.id.action_copy -> {
                val clipData = ClipData.newPlainText("文章链接", mArticle?.link)
                (getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(clipData)
                toast("链接已复制")
                return true
            }
            R.id.action_browser -> {
                val uri = Uri.parse(mArticle?.link)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                return true
            }
            R.id.action_refresh -> {
                mAgentWeb.urlLoader?.reload()
                return true
            }
        }
        return false
    }

    override fun onPrepareData() {
        super.onPrepareData()
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