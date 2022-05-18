package com.berker.feedu.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.berker.feedu.data.repository.RepositoryImplt
import com.berker.feedu.domain.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor() : ViewModel() {

    private lateinit var _feedFlow: Flow<PagingData<Person>>
    val feedFlow: Flow<PagingData<Person>>
        get() = _feedFlow

    fun getFeed() {
        viewModelScope.launch {
            _feedFlow = RepositoryImplt().getFeedWithNext()
        }
    }

}