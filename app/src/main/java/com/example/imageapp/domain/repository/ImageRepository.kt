package com.example.imageapp.domain.repository

import androidx.paging.PagingData

import com.example.imageapp.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface ImageRepository
{
  suspend fun  getEditorialFeedImage(): List<UnsplashImage>
//  chut apglu

  suspend fun getImage(imageId:String): UnsplashImage

  suspend fun searchImages (query: String)
  :Flow<PagingData<UnsplashImage>>



  suspend fun toggleFavoriteStatus(image: UnsplashImage)

  fun getFavoriteImagesId():Flow<List<String>>


  fun getAllFavImages (): Flow<PagingData<UnsplashImage>>


}