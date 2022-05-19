package com.berker.feedu.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.berker.feedu.data.local.DataSource
import com.berker.feedu.data.local.LocalPagingSource
import com.berker.feedu.data.local.Person
import com.berker.feedu.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class FeedRepositoryImpl : FeedRepository {

    override fun getFeedWithNext(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { LocalPagingSource(DataSource()) }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 15
    }
}

