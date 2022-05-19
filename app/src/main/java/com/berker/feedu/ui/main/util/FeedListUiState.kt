package com.berker.feedu.ui.main.util

import androidx.paging.LoadState
import com.berker.feedu.ui.base.BaseUiState

class FeedListUiState(
    private val loadState: LoadState,
    private val errorMessage: String =""
) : BaseUiState() {

    fun getProgressBarVisibility() = getViewVisibility(isVisible = loadState is LoadState.Loading)

    fun getRvVisibility() = getViewVisibility(isVisible = loadState is LoadState.NotLoading)

    fun getErrorVisibility() = getViewVisibility(isVisible = loadState is LoadState.Error)

    fun getErrorMessage() = (loadState is LoadState.Error).let {
        if (it) (loadState as LoadState.Error).error.message else ""
    }

}