package com.jiangyy.wanandroid.ui.article

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.jiangyy.common.view.BaseActivity
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.core.orDefault
import com.jiangyy.core.parcelableIntent
import com.jiangyy.dialog.StringBottomListDialog
import com.jiangyy.wanandroid.databinding.ActivityArticleBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.utils.DataStoreUtils
import com.jiangyy.wanandroid.utils.SharesFactory
import com.jiangyy.wanandroid.utils.htmlString
import com.just.agentweb.AgentWeb

class ArticleActivity : BaseActivity<ActivityArticleBinding>(ActivityArticleBinding::inflate) {

    private val mArticle by parcelableIntent<Article>("article")
    private lateinit var mAgentWeb: AgentWeb
    private val mViewModel by viewModels<ArticleViewModel>()

    override fun initWidget() {
        super.initWidget()
        binding.tvTitle.text = mArticle?.title.orEmpty().htmlString
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.frameLayout, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(mArticle?.link.orEmpty().replace("http:", "https:"))
        binding.toolbar.setOnEndListener {
            StringBottomListDialog()
                .items("收藏", "复制链接", "浏览器打开", "刷新", "微信", "朋友圈", "QQ", "QQ空间", "微信收藏") { position, _ ->
                    menuClick(position)
                }
                .show(supportFragmentManager)
        }
    }

    override fun initObserver() {
        super.initObserver()
        mViewModel.opResult.observe(this) {
            if (it.isSuccess) {
                mArticle?.collect = it.getOrNull()
                doneToast("操作成功")
            } else {
                errorToast(it.exceptionOrNull()?.message.orEmpty())
            }
        }
    }

    override fun preLoad() {
        super.preLoad()
        DataStoreUtils.scan(mArticle)
    }

    private fun menuClick(position: Int) {
        if (mArticle == null) return
        mArticle!!.let {
            when (position) {
                0 -> { // 收藏
                    if (it.collect.orDefault()) {
                        mViewModel.uncollect(it.id.orEmpty())
                    } else {
                        mViewModel.collect(it.id.orEmpty())
                    }
                }

                1 -> { // 复制链接
                    val clipData = ClipData.newPlainText("文章链接", it.link)
                    (getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(clipData)
                    doneToast("链接已复制")
                }

                2 -> { // 浏览器打开
                    val uri = Uri.parse(it.link)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }

                3 -> { // 刷新
                    mAgentWeb.urlLoader?.reload()
                }

                4 -> { // 微信
                    SharesFactory.shareToSession(it)
                }

                5 -> { // 朋友圈
                    SharesFactory.shareToTimeline(it)
                }

                6 -> { // QQ
                    SharesFactory.shareToQQ(this, it)
                }

                7 -> { // QQ空间
                    SharesFactory.shareToQzone(this, it)
                }

                8 -> { // 微信收藏
                    SharesFactory.shareToFavorite(it)
                }

                else -> Unit
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