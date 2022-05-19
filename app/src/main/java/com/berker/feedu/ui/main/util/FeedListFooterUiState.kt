package com.berker.feedu.ui.main.util

import android.content.Context
import androidx.paging.LoadState
import com.berker.feedu.ui.base.BaseUiState

data class FeedListFooterUiState(
    private val loadState: LoadState,
    private val errorMessage: String = ""
) : BaseUiState() {
    fun getLoadingVisibility() = getViewVisibility(isVisible = loadState is LoadState.Loading)

    fun getErrorVisibility() = getViewVisibility(isVisible = loadState is LoadState.Error)

    fun getErrorMessage(context: Context) = if (loadState is LoadState.Error) {
        errorMessage
    } else ""
}