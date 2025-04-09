package com.example.imageapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.imageapp.data.local.entity.FavoriteImageEntity
import kotlinx.coroutines.flow.Flow

@Dao interface FavoriteImagesDao
{


    @Query("SELECT * FROM favorite_images_table")
    fun getAllFavoriteImages(): PagingSource<Int, FavoriteImageEntity>

    // 1st used
    @Upsert
    suspend fun insertFavoriteImage(image: FavoriteImageEntity)

    // 2d used
    @Delete
    suspend fun deleteFavoriteImage(image: FavoriteImageEntity)

    // 3rd used
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_images_table WHERE id = :id)")
    suspend fun isImageFavorite(id: String): Boolean

    // 4th used
    @Query("SELECT id FROM favorite_images_table")
    fun getFavoriteImageIds(): Flow<List<String>>

}