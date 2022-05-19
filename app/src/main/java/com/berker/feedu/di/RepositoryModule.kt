package com.berker.feedu.di

import com.berker.feedu.data.repository.FeedRepositoryImpl
import com.berker.feedu.domain.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFeedRepository(): FeedRepository = FeedRepositoryImpl()
}