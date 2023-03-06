package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.kotlinapp.app.core.orDefault
import com.jiangyy.wanandroid.databinding.RecyclerItemArticleBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemProjectBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.htmlString

class ArticleAdapter : BaseMultiItemAdapter<Article>() {

    class ArticleVH(val binding: RecyclerItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
    class ProjectVH(val binding: RecyclerItemProjectBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        addItemType(0, object : OnMultiItemAdapterListener<Article, ArticleVH> {
            override fun onBind(holder: ArticleVH, position: Int, item: Article?) {
                holder.binding.tvArticleTitle.text = item?.title.htmlString
                if (item?.author.isNullOrEmpty()) {
                    holder.binding.tvArticleAuthor.text = "分享人：${item?.shareUser.orDefault("佚名")}"
                } else {
                    holder.binding.tvArticleAuthor.text = "作者：${item?.author.orDefault("佚名")}"
                }
                holder.binding.tvArticleDate.text = item?.niceDate.orEmpty()
                holder.binding.tvArticleTag.text = "${item?.superChapterName.orDefault("无")} · ${item?.chapterName.orDefault("无")}"
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ArticleVH {
                return ArticleVH(RecyclerItemArticleBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).addItemType(1, object : OnMultiItemAdapterListener<Article, ProjectVH> {
            override fun onBind(holder: ProjectVH, position: Int, item: Article?) {
                holder.binding.ivArticleImage.load(item?.envelopePic)
                holder.binding.tvArticleTitle.text = item?.title.htmlString
                if (item?.author.isNullOrEmpty()) {
                    holder.binding.tvArticleAuthor.text = "分享人：${item?.shareUser.orDefault("佚名")}"
                } else {
                    holder.binding.tvArticleAuthor.text = "作者：${item?.author.orDefault("佚名")}"
                }
                holder.binding.tvArticleDate.text = item?.niceDate.orEmpty()
                holder.binding.tvArticleTag.text = "${item?.superChapterName.orDefault("无")} · ${item?.chapterName.orDefault("无")}"
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ProjectVH {
                return ProjectVH(RecyclerItemProjectBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).onItemViewType { position, list -> if (list[position].envelopePic.isNullOrEmpty()) 0 else 1 }

    }

}