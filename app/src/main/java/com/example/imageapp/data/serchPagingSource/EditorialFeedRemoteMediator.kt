package com.example.imageapp.data.serchPagingSource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.imageapp.data.local.database.ImageVistaDatabase
import com.example.imageapp.data.local.entity.UnsplashImageEntity
import com.example.imageapp.data.remote.UnsplashApiService

@OptIn(ExperimentalPagingApi::class)
class EditorialFeedRemoteMediator(private val apiService: UnsplashApiService,
                                  private val database: ImageVistaDatabase) :
    RemoteMediator<Int, UnsplashImageEntity>()
{


    companion object
    {


        private const val STARTING_PAGE_INDEX = 1
    }

    private val database2 = database.favoriteImagesDao()

    override suspend fun load(loadType: LoadType,
                              state: PagingState<Int, UnsplashImageEntity>): MediatorResult
    {
        val currentPage = when (loadType)
        {
            LoadType.REFRESH ->
            {
            }

            LoadType.PREPEND ->
            {
            }

            LoadType.APPEND ->
            {
            }
        }


        try
        {


            val response = apiService.getEditorialFeedImages(page = currentPage, perPage = TODO())


        } catch (e: Exception)
        {
            return MediatorResult.Error(e)
        }
    }
}