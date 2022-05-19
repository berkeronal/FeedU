package com.berker.feedu.ui.main.util

import com.berker.feedu.data.local.Person
import com.berker.feedu.ui.base.BaseUiState

data class FeedListItemUiState(
    private val person: Person
) : BaseUiState() {

    fun getName() = "${person.fullName} (${person.id})"
}