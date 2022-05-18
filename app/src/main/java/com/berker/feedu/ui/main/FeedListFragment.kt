package com.berker.feedu.ui.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.berker.feedu.R
import com.berker.feedu.databinding.FragmentFeedListBinding
import com.berker.feedu.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest

class FeedListFragment : BaseFragment<FragmentFeedListBinding>() {

    private val viewModel: FeedListViewModel by viewModels()

    private var next = 0

    override fun initUi() {

        viewModel.getFeed()

        val feedAdapter = FeedAdapter()

        binding.rvFeed.apply {
            adapter = feedAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.feedFlow.collectLatest { pagingData ->
                feedAdapter.submitData(pagingData)
            }
        }
    }

    override fun layoutId() = R.layout.fragment_feed_list
}