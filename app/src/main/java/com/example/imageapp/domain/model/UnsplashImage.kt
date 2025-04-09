package com.example.imageapp.domain.model

data class UnsplashImage(val id: String,
                         val imageUrlSmall: String,
                         val imageUrlRegular: String,
                         val imageUrlRaw: String,
                         val photographerName: String,
                         val photographerUsername: String,
                         val photographerImageUrl: String,
                         val photographerProfileFileLink: String,
                         val width: Int,
                         val height: Int,
                         val description: String?)