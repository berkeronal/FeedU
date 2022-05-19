package com.berker.feedu.di

import com.berker.feedu.data.repository.FeedRepositoryImpl
import com.berker.feedu.domain.repository.FeedRepository
import com.berker.feedu.domain.usecase.FeedUseCases
import com.berker.feedu.domain.usecase.GetFeedWithPaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideFeedUseCases(repository: FeedRepository): FeedUseCases = FeedUseCases(
        getFeedWithPaging = GetFeedWithPaging(repository)
    )
}
