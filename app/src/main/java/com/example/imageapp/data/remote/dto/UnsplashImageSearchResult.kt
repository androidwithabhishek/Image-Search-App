package com.example.imageapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageSearchResult(

        @SerialName("results")
        val Images: List<UnsplashImageDto>,
        val total: Int,
        @SerialName("total_pages") val totalPages: Int)