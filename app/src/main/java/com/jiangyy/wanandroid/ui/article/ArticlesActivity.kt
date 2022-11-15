package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import com.jiangyy.core.parcelableIntent
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ActivityArticlesBinding
import com.jiangyy.wanandroid.entity.Tree

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>() {

    private val mTree by parcelableIntent<Tree>("tree")

    override fun initValue() {

    }

    override fun initWidget() {
        binding.toolbar.setTitle(mTree?.name.orEmpty())
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInTreeFragment.newInstance()).commit()
    }

    companion object {
        fun actionStart(context: Context, type: String) {
            Intent(context, ArticlesActivity::class.java).apply {
                this.putExtra("type", type)
                context.startActivity(this)
            }
        }

        fun actionStart(context: Context, tree: Tree?) {
            Intent(context, ArticlesActivity::class.java).apply {
                this.putExtra("tree", tree)
                context.startActivity(this)
            }
        }
    }

}