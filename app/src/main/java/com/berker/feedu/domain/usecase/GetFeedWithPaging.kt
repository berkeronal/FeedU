package com.berker.feedu.domain.usecase

import androidx.paging.PagingData
import com.berker.feedu.data.local.Person
import com.berker.feedu.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetFeedWithPaging(
    private val repository: FeedRepository
) {
    operator fun invoke(): Flow<PagingData<Person>> {
        return repository.getFeedWithNext()
    }
}