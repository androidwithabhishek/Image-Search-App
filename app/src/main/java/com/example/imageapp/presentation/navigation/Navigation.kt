package com.example.imageapp.presentation.navigation


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.imageapp.presentation.fullImageScreen.FullScreenViewModel

import com.example.imageapp.presentation.favScreen.FavViewModel
import com.example.imageapp.presentation.favScreen.FavoritesScreen

import com.example.imageapp.presentation.profileScreen.ProfileScreen
import com.example.imageapp.presentation.searchScreen.SearchScreen

import com.example.imageapp.presentation.searchScreen.SearchViewModel


import com.example.imageapp.presentation.fullImageScreen.FullImageScreen
import com.example.imageapp.presentation.homeScreen.HomeScreen
import com.example.imageapp.presentation.homeScreen.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackBarState: SnackbarHostState,

    topAppBarScrollBehavior: TopAppBarScrollBehavior,
) {


    NavHost(navController = navController, startDestination = Routes.HomeScreen) {

        composable<Routes.HomeScreen> {
            val viewModel: MainViewModel = hiltViewModel()
            val favoriteImageIdsHome by viewModel.favoriteImageIds.collectAsStateWithLifecycle()
            val images = viewModel.images.collectAsLazyPagingItems()
            var selectedIndex = remember { mutableStateOf(0) }


            HomeScreen(
                images = images,
                snackBarHostState = snackBarState,
                snackBarEvent = viewModel.snackBarEvent,
                scrollBehavior = topAppBarScrollBehavior,
                onImageClick = { imageId, index ->
                    navController.navigate(Routes.FullScreen(imageId = imageId, index = index))
                },
                onSearchClick = { navController.navigate(Routes.SearchScreen) },
                onFABClick = { navController.navigate(Routes.FavScreen) },
                favoriteImageIDs = favoriteImageIdsHome,
                toggleFavoriteStatus = {
                    viewModel.toggleFavoriteStatus(it)
                },

                )


        }


        composable<Routes.SearchScreen> {
            val viewModelSearch: SearchViewModel = hiltViewModel()
            val searchFeed = viewModelSearch.searchImages.collectAsLazyPagingItems()
            val favoriteImageIds by viewModelSearch.favoriteImageIds.collectAsStateWithLifecycle()
            SearchScreen(
                snackBarHostState = snackBarState,
                navHostController = navController,
                snackBarEvent = viewModelSearch.snackBarEvent,
                scrollBehavior = topAppBarScrollBehavior,
                images = searchFeed,
                onImageClick = { imageId, index ->
                    navController.navigate(
                        Routes.FullScreen(
                            imageId = imageId,
                            index = index
                        )
                    )
                },
                onSearch = {
                    viewModelSearch.searchImages(it)
                },
                toggleFavoriteStatus = {
                    viewModelSearch.toggleFavoriteStatus(it)
                },
                favoriteImageIDs = favoriteImageIds,
                onBackClick = { navController.popBackStack() }
            )


        }







        composable<Routes.FavScreen> {
            val favViewModel: FavViewModel = hiltViewModel()
            val favImages = favViewModel.favoriteImage.collectAsLazyPagingItems()
            val favoriteImageIds by favViewModel.favoriteImageIds.collectAsStateWithLifecycle()

            FavoritesScreen(
                snackBarHostState = snackBarState,
                navHostController = navController,
                snackBarEvent = favViewModel.snackBarEvent,
                scrollBehavior = topAppBarScrollBehavior,
                images = favImages,
                favoriteImageIDs = favoriteImageIds,


                onImageClick = { Imageid, Index ->

                    navController.navigate(
                        Routes.FullScreen(
                            imageId = Imageid,
                            index = Index
                        )
                    )
                },
                toggleFavoriteStatus = {

                    favViewModel.toggleFavoriteStatus(it)

                },
                onSearchClick = { navController.navigate(Routes.SearchScreen) })
        }

        composable<Routes.FullScreen> { backStackEntry ->


            val fullScreenViewModel: FullScreenViewModel = hiltViewModel()

            val image = fullScreenViewModel.image


            FullImageScreen(
                snackBarHostState = snackBarState,
                snackBarEvent = fullScreenViewModel.snackBarEvent,
                imageClass = image,
                onBackClick = { navController.navigateUp() },
                onPhotographerNameClick = { profileLink ->
                    navController.navigate(Routes.ProfileScreen(profileLink))
                },
                onImageDownloadClick = { url, title ->
                    fullScreenViewModel.downloadImage(url, title = title.toString())
                }
            )


        }

        composable<Routes.ProfileScreen> { backStackEntry ->

            val profileUrl = backStackEntry.toRoute<Routes.ProfileScreen>().profileLink

            ProfileScreen(profileLink = profileUrl, onBackClick = { navController.navigateUp() })
        }
    }
}