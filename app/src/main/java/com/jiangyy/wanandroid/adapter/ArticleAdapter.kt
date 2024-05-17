package com.jiangyy.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.jiangyy.wanandroid.databinding.ItemArticleBinding
import com.jiangyy.wanandroid.databinding.ItemProjectBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.entity.htmlString

class ArticleAdapter : BaseMultiItemAdapter<Article>() {

    class VH1(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
    class VH2(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        addItemType(0, object : OnMultiItemAdapterListener<Article, VH1> {
            override fun onBind(holder: VH1, position: Int, item: Article?) {
                holder.binding.tvChapter.text = "${item?.superChapterName} · ${item?.chapterName}"
                holder.binding.tvTitle.text = item?.title.htmlString
                holder.binding.tvDate.text = item?.niceDate
                holder.binding.tvAuthor.text = item?.author
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): VH1 {
                return VH1(ItemArticleBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).addItemType(1, object : OnMultiItemAdapterListener<Article, VH2> {
            override fun onBind(holder: VH2, position: Int, item: Article?) {
                holder.binding.tvChapter.text = "${item?.superChapterName} · ${item?.chapterName}"
                holder.binding.tvTitle.text = item?.title.htmlString
                holder.binding.tvDate.text = item?.niceDate
                holder.binding.tvAuthor.text = item?.author
                holder.binding.iv.load(item?.envelopePic)
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): VH2 {
                return VH2(ItemProjectBinding.inflate(LayoutInflater.from(context), parent, false))
            }
        }).onItemViewType { position, list -> if (list[position].envelopePic.isNullOrEmpty()) 0 else 1 }
    }

}