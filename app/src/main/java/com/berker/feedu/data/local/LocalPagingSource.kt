package com.berker.feedu.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.berker.feedu.util.Resource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val STARTING_INDEX = 1
private const val DEFAULT_TIMEOUT = 3000L

class LocalPagingSource(
    private val dataSource: DataSource
) : PagingSource<Int, Person>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val position = params.key ?: STARTING_INDEX

        return try {

            when (val response = getDataFromDataSource(position)) {
                is Resource.Error -> {
                    LoadResult.Error(Exception(response.message))
                }
                is Resource.Success -> {
                    LoadResult.Page(
                        data = response.data.people,
                        prevKey = if (position == STARTING_INDEX) null else position - 1,
                        nextKey = response.data.next?.toInt()
                    )
                }
            }

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private suspend fun getDataFromDataSource(position: Int): Resource<FetchResponse> {
        return suspendCoroutine<Resource<FetchResponse>> { cont ->
            dataSource.fetch(
                next = position.toString(),
                completionHandler = { fetchResponse, fetchError ->
                    fetchResponse?.let {
                        println(it.people)
                        cont.resume(
                            Resource.Success(
                                data = it
                            )
                        )
                    } ?: fetchError?.let {
                        println(fetchError.errorDescription)
                        cont.resume(Resource.Error(it.errorDescription))
                    }
                }
            )
        }
    }
}
