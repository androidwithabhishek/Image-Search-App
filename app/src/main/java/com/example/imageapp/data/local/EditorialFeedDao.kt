package com.example.imageapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.imageapp.data.local.entity.UnsplashImageEntity

@Dao interface EditorialFeedDao
{


    @Query("SELECT *FROM image_entity_table")
    fun getAllFeeImages(): PagingSource<Int, UnsplashImageEntity>


    @Upsert
    suspend fun insetFeedImages(images: List<UnsplashImageEntity>)


    @Query("DELETE fROM image_entity_table")
    suspend fun deleteFeedImages()


}