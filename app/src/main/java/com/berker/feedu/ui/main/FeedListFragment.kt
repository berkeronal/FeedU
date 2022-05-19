package com.berker.feedu.ui.main

import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.berker.feedu.R
import com.berker.feedu.data.local.Person
import com.berker.feedu.databinding.FragmentFeedListBinding
import com.berker.feedu.ui.base.BaseFragment
import com.berker.feedu.ui.main.adapter.FeedAdapter
import com.berker.feedu.ui.main.adapter.FooterAdapter
import com.berker.feedu.ui.main.util.FeedListUiState
import com.berker.feedu.util.collect
import com.berker.feedu.util.executeWithAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class FeedListFragment : BaseFragment<FragmentFeedListBinding>() {

    private val viewModel: FeedListViewModel by viewModels()

    @Inject
    lateinit var feedAdapter: FeedAdapter

    private var next = 0

    override fun initUi() {
        viewModel.getFeed()

        initRecyclerView()
        initSwipeRefresh()

        binding.btnRetry.setOnClickListener {
            feedAdapter.refresh()
        }
    }

    override fun initReceivers() {
        collect(viewModel.feedFlow, ::setRecyclerViewData)

        collect(flow = feedAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setFeedListUiState
        )
    }

    override fun layoutId() = R.layout.fragment_feed_list

    private fun initRecyclerView() {
        binding.rvFeed.apply {

            adapter = feedAdapter.withLoadStateFooter(
                FooterAdapter(feedAdapter::retry)
            )
        }
    }

    private suspend fun setRecyclerViewData(pagingData: PagingData<Person>) {
        feedAdapter.submitData(pagingData)

        if (feedAdapter.itemCount == 0) {
            setFeedListUiState(LoadState.Error(Exception("List is empty, try to reload")))
        }
    }

    private fun setFeedListUiState(loadState: LoadState) {

        binding.executeWithAction {
            feedListUiState = FeedListUiState(loadState)
        }
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