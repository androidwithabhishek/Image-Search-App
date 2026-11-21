package com.example.imageapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes
{
    @Serializable
    object HomeScreen: Routes()


    @Serializable
    data object SearchScreen : Routes()


    @Serializable
    data object FavScreen : Routes()


    @Serializable
    data class FullScreen(val imageId: String, val index:Int) : Routes()

    @Serializable
    data class ProfileScreen(val profileLink: String) : Routes()
}