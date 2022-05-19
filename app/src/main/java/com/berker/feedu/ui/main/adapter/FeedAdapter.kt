package com.berker.feedu.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.berker.feedu.databinding.ItemRcBinding
import com.berker.feedu.domain.model.Person
import com.berker.feedu.ui.main.util.FeedListItemUiState

class FeedAdapter : PagingDataAdapter<Person, FeedAdapter.FeedViewHolder>(FeedComparator) {

    inner class FeedViewHolder(private val binding: ItemRcBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Person) = with(binding) {
            feedListItemUiState = FeedListItemUiState(person = item)
        }
    }

    object FeedComparator : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Person, newItem: Person) =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder =
        FeedViewHolder(
            ItemRcBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
}