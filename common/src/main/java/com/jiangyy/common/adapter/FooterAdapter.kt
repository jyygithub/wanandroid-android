package com.jiangyy.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.jiangyy.common.databinding.RecyclerFooterBinding

class FooterAdapter(private val retry: () -> Unit) : LoadStateAdapter<BaseViewHolder<RecyclerFooterBinding>>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BaseViewHolder<RecyclerFooterBinding> {
        val binding = RecyclerFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<RecyclerFooterBinding>, loadState: LoadState) {
        holder.binding.loadingProgress.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        holder.binding.loadingText.text = when (loadState) {
            is LoadState.Loading -> "正在加载"
            is LoadState.Error -> "点击重试"
            is LoadState.NotLoading -> "已全部加载"
        }
        holder.binding.loadingText.setOnClickListener {
            if (loadState is LoadState.Error) {
                retry.invoke()
            }
        }
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return !(loadState is LoadState.NotLoading && !loadState.endOfPaginationReached)
    }
}