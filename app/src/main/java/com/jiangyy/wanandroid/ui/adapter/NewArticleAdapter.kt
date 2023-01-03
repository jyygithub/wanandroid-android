package com.jiangyy.wanandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.jiangyy.core.orDefault
import com.jiangyy.core.orZero
import com.jiangyy.viewbinding.adapter.BaseVBPagingDataAdapter
import com.jiangyy.wanandroid.databinding.RecyclerItemArticleBinding
import com.jiangyy.wanandroid.databinding.RecyclerItemProjectBinding
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.utils.htmlString

class NewArticleAdapter : BaseVBPagingDataAdapter<Article>({ it.id }) {

    private var block: ((Int) -> Unit)? = null

    fun setOnItemLongClickListener(block: (Int) -> Unit) {
        this.block = block
    }

    override fun convert(binding: ViewBinding, position: Int) {
        val item = getItem(position)!!
        binding.root.setOnLongClickListener {
            block?.invoke(position)
            false
        }
        if (getItemViewType(position) == 0) {
            (binding as RecyclerItemArticleBinding).let {
                it.tvArticleTitle.text = item.title.orEmpty().htmlString
                if (item.author.isNullOrEmpty()) {
                    it.tvArticleAuthor.text = "分享人：${item.shareUser.orDefault("佚名")}"
                } else {
                    it.tvArticleAuthor.text = "作者：${item.author.orDefault("佚名")}"
                }
                it.tvArticleDate.text = item.niceDate.orEmpty()
                it.tvArticleTag.text = "${item.superChapterName.orDefault("无")} · ${item.chapterName.orDefault("无")}"
            }
        } else {
            (binding as RecyclerItemProjectBinding).let {
                it.ivArticleImage.load(item.envelopePic)
                it.tvArticleTitle.text = item.title.orEmpty().htmlString
                if (item.author.isNullOrEmpty()) {
                    it.tvArticleAuthor.text = "分享人：${item.shareUser.orDefault("佚名")}"
                } else {
                    it.tvArticleAuthor.text = "作者：${item.author.orDefault("佚名")}"
                }
                it.tvArticleDate.text = item.niceDate.orEmpty()
                it.tvArticleTag.text = "${item.superChapterName.orDefault("无")} · ${item.chapterName.orDefault("无")}"
            }

        }
    }

    fun removeAt(position: Int) {
        this.notifyItemRemoved(position)
    }

    override fun onCreateViewBinding(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): ViewBinding {
        return if (viewType == 0) {
            RecyclerItemArticleBinding.inflate(inflater, parent, false)
        } else {
            RecyclerItemProjectBinding.inflate(inflater, parent, false)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.itemType.orZero()
    }

}