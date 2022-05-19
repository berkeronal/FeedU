package com.berker.feedu.ui.main.util

import androidx.paging.LoadState
import com.berker.feedu.ui.base.BaseUiState

data class FeedListFooterUiState(
    private val loadState: LoadState,
) : BaseUiState() {
    fun getLoadingVisibility() = getViewVisibility(isVisible = loadState is LoadState.Loading)

    fun getErrorVisibility() = getViewVisibility(isVisible = loadState is LoadState.Error)

    fun getErrorMessage() = (loadState is LoadState.Error).let {
        if (it) (loadState as LoadState.Error).error.message else ""
    }
}