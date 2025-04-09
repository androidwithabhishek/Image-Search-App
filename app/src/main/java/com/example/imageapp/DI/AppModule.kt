package com.example.imageapp.DI

import android.content.Context
import androidx.room.Room
import com.example.imageapp.data.local.database.ImageVistaDatabase
import com.example.imageapp.data.remote.UnsplashApiService
import com.example.imageapp.data.utils.Constants.BASE_URL
import com.example.imageapp.data.utils.Constants.IMAGE_VISTA_DATABASE
import com.example.imageapp.data.repositoryImpl.ImplAndroidImageDownloader
import com.example.imageapp.data.repositoryImpl.ImplementationImageRepository
import com.example.imageapp.data.repositoryImpl.NetworkConnectivityObserverImpl
import com.example.imageapp.domain.repository.Downloader
import com.example.imageapp.domain.repository.ImageRepository
import com.example.imageapp.domain.repository.NetworkConnectivityObserver
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module @InstallIn(SingletonComponent::class) object AppModule
{


    @Provides
    @Singleton
    fun providesUnsplashApiService(): UnsplashApiService
    {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder().addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BASE_URL).build()
        return retrofit.create(UnsplashApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideImageVistaDatabase(@ApplicationContext context: Context): ImageVistaDatabase
    {
        return Room.databaseBuilder(context, ImageVistaDatabase::class.java, IMAGE_VISTA_DATABASE)
            .build()
    }

    @Provides
    @Singleton
    fun provideImageRepository(apiService: UnsplashApiService,
                               database: ImageVistaDatabase): ImageRepository
    {
        return ImplementationImageRepository(apiService, database)
    }

    @Provides
    @Singleton
    fun provideImageDownloader(@ApplicationContext context: Context): Downloader
    {

        return ImplAndroidImageDownloader(context)
    }

    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope
    {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(@ApplicationContext context: Context,
                                           scope: CoroutineScope): NetworkConnectivityObserver
    {
        return NetworkConnectivityObserverImpl(context, scope)
    }


}