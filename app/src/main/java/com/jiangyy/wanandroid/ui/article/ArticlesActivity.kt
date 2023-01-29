package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.jiangyy.common.view.BaseActivity
import com.jiangyy.dialog.ConfirmDialog
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ActivityArticlesBinding
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.ui.user.CollectionHistoryFragment
import com.jiangyy.wanandroid.ui.user.ScanHistoryFragment
import com.jiangyy.wanandroid.ui.user.ShareActivity
import com.jiangyy.wanandroid.ui.user.ShareHistoryFragment
import com.jiangyy.wanandroid.utils.DataStoreUtils
import com.jiangyy.wanandroid.utils.parcelableIntent
import com.jiangyy.wanandroid.utils.stringIntent

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>(ActivityArticlesBinding::inflate) {

    private val mTree by parcelableIntent<Tree>("tree")
    private val mType by stringIntent("type")
    private val mArticlesViewModel by viewModels<ArticlesViewModel>()

    override fun initWidget() {
        super.initWidget()
        binding.toolbar.setTitle(mTree?.name.orEmpty())
        when (mType) {
            "tree" -> supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInTreeFragment.newInstance())
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
                binding.toolbar.setEnd(ContextCompat.getDrawable(this, R.drawable.ic_share), null)
                binding.toolbar.setOnEndListener {
                    ShareActivity.actionStart(this)
                }
            }

            "collection" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, CollectionHistoryFragment.newInstance())
                    .commit()
                binding.toolbar.setTitle("我的收藏")
            }

            "scan" -> {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, ScanHistoryFragment.newInstance()).commit()
                binding.toolbar.setTitle("我的浏览")
                binding.toolbar.setEnd(ContextCompat.getDrawable(this, R.drawable.ic_clear), null)
                binding.toolbar.setOnEndListener {
                    ConfirmDialog()
                        .bindConfig {
                            title = "提示"
                            content = "确认清空浏览记录"
                        }
                        .confirm {
                            DataStoreUtils.clearScan()
                            mArticlesViewModel.clearScan()
                        }
                        .show(supportFragmentManager)
                }
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