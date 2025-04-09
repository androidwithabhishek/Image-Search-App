package com.example.imageapp.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.imageapp.data.local.database.ImageVistaDatabase
import com.example.imageapp.data.maper.toDomainModel
import com.example.imageapp.data.maper.toDomainModelList
import com.example.imageapp.data.maper.toFavoriteImageEntity
import com.example.imageapp.data.remote.UnsplashApiService
import com.example.imageapp.data.utils.Constants.ITEMS_PER_PAGE
import com.example.imageapp.data.serchPagingSource.SearchPagingSource
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ImplementationImageRepository(private val unsplashApi: UnsplashApiService,
                                    private val database: ImageVistaDatabase) : ImageRepository
{


    private val favoriteImagesDao = database.favoriteImagesDao()


    override suspend fun getEditorialFeedImage(): List<UnsplashImage>
    {
        return unsplashApi.getEditorialFeedImages().toDomainModelList()
    }

    override suspend fun getImage(imageId: String): UnsplashImage
    {
        return unsplashApi.getImage(imageId).toDomainModel()
    }

    override suspend fun searchImages(query: String): Flow<PagingData<UnsplashImage>>
    {
        return Pager(config = PagingConfig(pageSize = ITEMS_PER_PAGE), pagingSourceFactory = {
            SearchPagingSource(query = query, unsplashApi)
        }).flow
    }

    override suspend fun toggleFavoriteStatus(image: UnsplashImage)
    {
        val isFavorite = favoriteImagesDao.isImageFavorite(id = image.id)

        val favoriteImage = image.toFavoriteImageEntity()


        if (isFavorite)
        {
            favoriteImagesDao.deleteFavoriteImage(favoriteImage)
        }
        else
        {
            favoriteImagesDao.insertFavoriteImage(favoriteImage)
        }

    }


    override fun getFavoriteImagesId(): Flow<List<String>>
    {
        return favoriteImagesDao.getFavoriteImageIds()
    }

    override fun getAllFavImages(): Flow<PagingData<UnsplashImage>>
    {
        return Pager(config = PagingConfig(pageSize = ITEMS_PER_PAGE), pagingSourceFactory = {
            favoriteImagesDao.getAllFavoriteImages()
        }).flow.map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }
}