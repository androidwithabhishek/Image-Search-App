package com.example.imageapp.domain.repository

import androidx.paging.PagingData

import com.example.imageapp.domain.model.DomainUnsplashImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository
{
   fun  getEditorialFeedImage(): Flow<PagingData<DomainUnsplashImage>>


  suspend fun getImage(imageId:String): DomainUnsplashImage

  suspend fun searchImages (query: String)
  :Flow<PagingData<DomainUnsplashImage>>



  suspend fun toggleFavoriteStatus(image: DomainUnsplashImage)

  fun getFavoriteImagesId():Flow<List<String>>


  fun getAllFavPagingImages (): Flow<PagingData<DomainUnsplashImage>>


}