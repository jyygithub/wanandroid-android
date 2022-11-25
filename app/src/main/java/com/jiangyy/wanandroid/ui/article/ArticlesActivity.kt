package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import com.jiangyy.core.parcelableIntent
import com.jiangyy.core.stringIntent
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ActivityArticlesBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.user.CollectionHistoryFragment
import com.jiangyy.wanandroid.ui.user.ScanHistoryFragment
import com.jiangyy.wanandroid.ui.user.ShareHistoryFragment

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>() {

    private val mTree by parcelableIntent<Tree>("tree")
    private val mType by stringIntent("type")

    override fun initValue() {

    }

    override fun initWidget() {
        binding.toolbar.setTitle(mTree?.name.orEmpty())
        when (mType) {
            "tree" -> supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInTreeFragment.newInstance())
                .commit()
            "wechat" -> supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInWechatFragment.newInstance())
                .commit()
            "wenda" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInWendaFragment.newInstance()).commit()
                binding.toolbar.setTitle("每日一问")
            }
            "square" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInSquareFragment.newInstance()).commit()
                binding.toolbar.setTitle("广场")
            }
            "sub" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInSubFragment.newInstance()).commit()
            }
            "share" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ShareHistoryFragment.newInstance()).commit()
                binding.toolbar.setTitle("我的分享")
            }
            "collection" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, CollectionHistoryFragment.newInstance()).commit()
                binding.toolbar.setTitle("我的收藏")
            }
            "scan" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ScanHistoryFragment.newInstance()).commit()
                binding.toolbar.setTitle("我的浏览")
            }
        }
    }

    companion object {
        fun actionStart(context: Context, type: String) {
            Intent(context, ArticlesActivity::class.java).apply {
                this.putExtra("type", type)
                context.startActivity(this)
            }
        }

        fun actionStart(context: Context, type: String?, tree: Tree?) {
            Intent(context, ArticlesActivity::class.java).apply {
                this.putExtra("tree", tree)
                this.putExtra("type", type)
                context.startActivity(this)
            }
        }
    }

}