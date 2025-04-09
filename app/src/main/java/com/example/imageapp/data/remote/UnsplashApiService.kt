package com.example.imageapp.data.remote

import com.example.imageapp.data.remote.dto2.UnsplashImageDto
import com.example.imageapp.data.remote.dto2.UnsplashImageSearchResult
import com.example.imageapp.data.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApiService
{

    @Headers("Authorization:Client-ID $API_KEY ") @GET("/photos")
    suspend fun getEditorialFeedImages( @Query("page") page: Int,
                                        @Query("per_page") perPage: Int): List<UnsplashImageDto>

    @Headers("Authorization:Client-ID $API_KEY ") @GET("/search/photos") suspend fun searchImage(
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int): UnsplashImageSearchResult


    @Headers("Authorization:Client-ID $API_KEY ") @GET("/photos/{id}") suspend fun getImage(
            @Path("id") imageId: String): UnsplashImageDto





//suspend fun getImage(imageId:String):DataClassItems
}