package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.core.intentParcelable
import com.koonny.appcompat.core.intentString
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ActivityArticlesBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.home.home.ArticleInSquareFragment
import com.jiangyy.wanandroid.ui.home.home.ArticleInWendaFragment

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>(ActivityArticlesBinding::inflate) {

    private val mTree by intentParcelable<Tree>("tree")
    private val mType by intentString("type")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle(mTree?.name.orEmpty())
        when (mType) {
            "tree" -> supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInTreeFragment.newInstance()).commit()
            "square" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInSquareFragment.newInstance()).commit()
                binding.toolbar.setTitle("广场")
            }
            "wenda" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInWendaFragment.newInstance()).commit()
                binding.toolbar.setTitle("每日一问")
            }
            "collection" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInCollectionFragment.newInstance()).commit()
                binding.toolbar.setTitle("我的收藏")
            }
            "share" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInShareFragment.newInstance()).commit()
                binding.toolbar.setTitle("我的分享")
            }
            "scan" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInScanFragment.newInstance()).commit()
                binding.toolbar.setTitle("浏览记录")
            }
            "user" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInUserFragment.newInstance()).commit()
                binding.toolbar.setTitle("用户分享记录")
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

        fun actionStart(context: Context, type: String?, userId: Int?) {
            Intent(context, ArticlesActivity::class.java).apply {
                this.putExtra("userId", userId)
                this.putExtra("type", type)
                context.startActivity(this)
            }
        }
    }

}