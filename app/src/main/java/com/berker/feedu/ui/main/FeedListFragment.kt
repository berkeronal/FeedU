package com.berker.feedu.ui.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.berker.feedu.R
import com.berker.feedu.databinding.FragmentFeedListBinding
import com.berker.feedu.ui.base.BaseFragment
import com.berker.feedu.ui.main.adapter.FeedAdapter
import com.berker.feedu.ui.main.adapter.FooterAdapter
import com.berker.feedu.ui.main.util.FeedListUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedListFragment : BaseFragment<FragmentFeedListBinding>() {

    private val viewModel: FeedListViewModel by viewModels()

    private var feedAdapter = FeedAdapter()

    private var next = 0

    override fun initUi() {
        viewModel.getFeed()
        initRecyclerView()
        initSwipeRefresh()

    }

    override fun initReceivers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.feedFlow.collectLatest { pagingData ->
                feedAdapter.submitData(pagingData)
            }
        }
    }

    override fun layoutId() = R.layout.fragment_feed_list

    private fun initRecyclerView() {

        handleListErrors()
        binding.rvFeed.apply {
            adapter = feedAdapter.withLoadStateFooter(FooterAdapter(feedAdapter::retry))
        }
    }

    private fun handleListErrors() {

        lifecycleScope.launch {
            feedAdapter.loadStateFlow.distinctUntilChangedBy { it.source.refresh }
                .map { it.refresh }
                .collectLatest {
                    setFeedListUiState(it)
                }
        }
    }

    private fun setFeedListUiState(loadState: LoadState) {
        binding.feedListUiState = FeedListUiState(loadState)
        binding.executePendingBindings()

    }


    private fun initSwipeRefresh() {
        binding.swiperefresh.apply {
            setOnRefreshListener {
                feedAdapter.refresh()
                isRefreshing = false
            }
        }
    }
}