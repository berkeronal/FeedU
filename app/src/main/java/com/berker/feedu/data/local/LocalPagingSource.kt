package com.berker.feedu.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.berker.feedu.domain.model.Person
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val STARTING_INDEX = 1

class LocalPagingSource : PagingSource<Int, Person>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val position = params.key ?: STARTING_INDEX

        var isSuccess = false
        var fetchedResponse: FetchResponse? = null
        var fetchedError: FetchError? = null

        return try {

            val asd = suspendCoroutine<FetchResponse> {cont->
                DataSource().fetch(
                    next = position.toString(),
                    completionHandler = { fetchResponse, fetchError ->
                        fetchResponse?.let {
                            println(it.people)
                            fetchedResponse = it
                            isSuccess = true
                            cont.resume(it)
                        } ?: fetchError?.let {
                            println(fetchError.errorDescription)
                        }

                    }
                )
            }
            return LoadResult.Page(
                data = fetchedResponse?.people ?: emptyList(),
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = fetchedResponse?.next?.toInt()
            )


        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
