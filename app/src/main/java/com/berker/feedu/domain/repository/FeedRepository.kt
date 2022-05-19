package com.berker.feedu.domain.repository

import androidx.paging.PagingData
import com.berker.feedu.data.local.Person
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getFeedWithNext(): Flow<PagingData<Person>>

}