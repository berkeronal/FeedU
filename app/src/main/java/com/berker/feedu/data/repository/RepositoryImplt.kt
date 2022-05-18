package com.berker.feedu.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.berker.feedu.data.local.LocalPagingSource
import com.berker.feedu.domain.model.Person
import com.berker.feedu.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class RepositoryImplt : FeedRepository {


    override fun getFeedWithNext(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = 13,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { LocalPagingSource() }
        ).flow
    }
}