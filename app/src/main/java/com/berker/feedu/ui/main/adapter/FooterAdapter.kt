package com.berker.feedu.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.berker.feedu.R
import com.berker.feedu.databinding.ItemPagingFooterBinding
import com.berker.feedu.ui.main.adapter.FooterAdapter.FooterViewHolder
import com.berker.feedu.ui.main.util.FeedListFooterUiState

class FooterAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<FooterViewHolder>() {

    inner class FooterViewHolder(
        private val binding: ItemPagingFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.footerUiState = FeedListFooterUiState(loadState)
        }
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        val itemPagingFooterBinding = DataBindingUtil.inflate<ItemPagingFooterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_paging_footer,
            parent,
            false
        )
        return FooterViewHolder(itemPagingFooterBinding, retry)
    }
}

