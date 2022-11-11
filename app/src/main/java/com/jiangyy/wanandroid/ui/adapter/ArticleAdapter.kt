package com.jiangyy.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.jiangyy.core.orDefault
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.utils.htmlString
import com.jiangyy.wanandroid.entity.Article

class ArticleAdapter : BaseMultiItemQuickAdapter<Article, AdapterViewHolder>(), LoadMoreModule {

    init {
        addItemType(0, R.layout.recycler_item_article)
        addItemType(1, R.layout.recycler_item_project)
    }

    override fun convert(holder: AdapterViewHolder, item: Article) {
        when (holder.itemViewType) {
            0 -> {
                holder.setText(R.id.tvArticleTitle, item.title.orEmpty().htmlString)
                if (item.author.isNullOrEmpty()) {
                    holder.setText(R.id.tvArticleAuthor, "分享人：${item.shareUser.orDefault("佚名")}")
                } else {
                    holder.setText(R.id.tvArticleAuthor, "作者：${item.author.orDefault("佚名")}")
                }
                holder.setText(R.id.tvArticleDate, item.niceDate.orEmpty())
                holder.setText(R.id.tvArticleTag, "${item.superChapterName.orDefault("无")} · ${item.chapterName.orDefault("无")}")
            }
            1 -> {
                holder.setImageUrl(R.id.ivArticleImage, item.envelopePic)
                holder.setText(R.id.tvArticleTitle, item.title.orEmpty().htmlString)
                if (item.author.isNullOrEmpty()) {
                    holder.setText(R.id.tvArticleAuthor, "分享人：${item.shareUser.orDefault("佚名")}")
                } else {
                    holder.setText(R.id.tvArticleAuthor, "作者：${item.author.orDefault("佚名")}")
                }
                holder.setText(R.id.tvArticleDate, item.niceDate.orEmpty())
                holder.setText(R.id.tvArticleTag, "${item.superChapterName.orDefault("无")} · ${item.chapterName.orDefault("无")}")
            }
            else -> Unit
        }
    }

}