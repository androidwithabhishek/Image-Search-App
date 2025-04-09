package com.example.imageapp.data.serchPagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imageapp.data.maper.toDomainModelList
import com.example.imageapp.data.remote.UnsplashApiService
import com.example.imageapp.domain.model.UnsplashImage

class SearchPagingSource(private val query: String,
                         private val unsplashApiService: UnsplashApiService) :
    PagingSource<Int, UnsplashImage>()
{

    override fun getRefreshKey (state : PagingState<Int, UnsplashImage>): Int?
    {
        return state.anchorPosition
    }

    companion object
    {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage>
    {
        val currentPage = params.key ?: STARTING_PAGE_INDEX


        return try
        {

            val response = unsplashApiService.searchImage(query = query,
                                                          page = currentPage,
                                                          perPage = params.loadSize)

            val endOFPaginationReached = response.Images.isEmpty()

            LoadResult.Page(data = response.Images.toDomainModelList(),
                            prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                            nextKey = if (endOFPaginationReached) null else currentPage + 1)

        } catch (e: Exception)
        {
            LoadResult.Error(e)
        }
    }
}