package com.berker.feedu.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.berker.feedu.domain.model.Person
import com.berker.feedu.domain.usecase.FeedUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor(
    val feedUseCases: FeedUseCases
) : ViewModel() {

    private lateinit var _feedFlow: Flow<PagingData<Person>>
    val feedFlow: Flow<PagingData<Person>>
        get() = _feedFlow

    fun getFeed() {

        _feedFlow = feedUseCases.getFeedWithPaging().map {
            val personMap = mutableSetOf<Int>()
            it.filter { person ->
                if (personMap.contains(person.id)) false else personMap.add(person.id)
            }
        }.cachedIn(viewModelScope)

    }

}