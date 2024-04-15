package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.chad.library.adapter4.BaseMultiItemAdapter
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
                    holder.binding.tvArticleAuthor.text = item?.shareUser ?: "佚名"
                } else {
                    holder.binding.tvArticleAuthor.text = item?.author ?: "佚名"
                }
                holder.binding.tvArticleDate.text = item?.niceDate.orEmpty()
                if (!item?.superChapterName.isNullOrEmpty() && !item?.chapterName.isNullOrEmpty()) {
                    holder.binding.tvArticleTag.text = "${item?.superChapterName} · ${item?.chapterName}"
                } else {
                    holder.binding.tvArticleTag.text = "${item?.superChapterName.orEmpty()}${item?.chapterName.orEmpty()}"
                }
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ArticleVH {
                return ArticleVH(RecyclerItemArticleBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).addItemType(1, object : OnMultiItemAdapterListener<Article, ProjectVH> {
            override fun onBind(holder: ProjectVH, position: Int, item: Article?) {
                holder.binding.ivArticleImage.load(item?.envelopePic)
                holder.binding.tvArticleTitle.text = item?.title.htmlString
                if (item?.author.isNullOrEmpty()) {
                    holder.binding.tvArticleAuthor.text = item?.shareUser ?: "佚名"
                } else {
                    holder.binding.tvArticleAuthor.text = item?.author ?: "佚名"
                }
                holder.binding.tvArticleDate.text = item?.niceDate.orEmpty()
                holder.binding.tvArticleTag.text =
                    "${item?.superChapterName ?: "无"} · ${item?.chapterName ?: "无"}"
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ProjectVH {
                return ProjectVH(RecyclerItemProjectBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).onItemViewType { position, list -> if (list[position].envelopePic.isNullOrEmpty()) 0 else 1 }

    }

}