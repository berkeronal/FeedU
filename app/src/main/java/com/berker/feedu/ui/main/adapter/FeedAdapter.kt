package com.berker.feedu.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.berker.feedu.R
import com.berker.feedu.data.local.Person
import com.berker.feedu.databinding.ItemRcBinding
import com.berker.feedu.ui.main.util.FeedListItemUiState
import com.berker.feedu.util.executeWithAction
import javax.inject.Inject

class FeedAdapter @Inject constructor() :
    PagingDataAdapter<Person, FeedAdapter.FeedViewHolder>(FeedComparator) {

    inner class FeedViewHolder(private val binding: ItemRcBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Person) = with(binding) {
            executeWithAction {
                feedListItemUiState =  FeedListItemUiState(person = item)
            }
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
            DataBindingUtil.inflate<ItemRcBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_rc,
                parent,
                false
            )
        )
}