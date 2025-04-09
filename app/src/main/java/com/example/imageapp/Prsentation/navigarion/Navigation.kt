package com.example.imageapp.Prsentation.navigarion


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.imageapp.Prsentation.FulllImageScreen.FullScreenViewModel
import com.example.imageapp.Prsentation.HomeScreen.HomeScreen
import com.example.imageapp.Prsentation.HomeScreen.MainViewModel
import com.example.imageapp.Prsentation.fevScreen.FavViewMode
import com.example.imageapp.Prsentation.fevScreen.FevScreen
import com.example.imageapp.Prsentation.prophilScreen.ProfileScreen
import com.example.imageapp.Prsentation.searchScreen.SearchScreen
import com.example.imageapp.Prsentation.searchScreen.SearchViewMode
import com.example.imagevista.presentation.full_image_screen.FullImageScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(modifier: Modifier = Modifier,
               navController: NavHostController,
               snackbarState: SnackbarHostState,

               topAppBarScrollBehavior: TopAppBarScrollBehavior)
{

    val viewModel: MainViewModel = hiltViewModel()

    val images = viewModel.images

    NavHost(navController = navController, startDestination = Routes.HomeScreen) {

        composable<Routes.HomeScreen> {

            HomeScreen(
                    images = images,
                    snackbarHostState = snackbarState,
                    snackbarEvent = viewModel.snackbarEvent,
                    scrollBehavior = topAppBarScrollBehavior,
                    onImageClick = { Imageid ->

                        navController.navigate(Routes.FullScreen(imageId = Imageid))


                    },
                    navController = navController,
                    onSearchClick = { navController.navigate(Routes.SearchScreen) },
                    onFABClick = { navController.navigate(Routes.FevScreen) },
            )


        }


        composable<Routes.SearchScreen> {
            val viewModelSearch: SearchViewMode = hiltViewModel()
            val searchFeed = viewModelSearch.searchImages.collectAsLazyPagingItems()
            val favoriteImageIds by viewModelSearch.favoriteImageIds.collectAsStateWithLifecycle()

            SearchScreen(snackbarHostState = snackbarState,
                         navHostController = navController,
                         snackbarEvent = viewModelSearch.snackbarEvent,
                         scrollBehavior = topAppBarScrollBehavior,
                         images = searchFeed,
                         onImageClick = { Imageid ->

                             navController.navigate(Routes.FullScreen(imageId = Imageid))
                         },
                         onSearch = {
                             viewModelSearch.searchImages(it)
                         },
                         toggleFavoriteStatus = {

                             viewModelSearch.toggleFavoriteStatus(it)

                         },
                         favoriteImageIDs = favoriteImageIds)
        }







        composable<Routes.FevScreen> {
            val favViewMode:FavViewMode = hiltViewModel()
            val favImages = favViewMode.favoriteImage.collectAsLazyPagingItems()
            val favoriteImageIds by favViewMode.favoriteImageIds.collectAsStateWithLifecycle()
            FevScreen(snackbarHostState = snackbarState,
                      navHostController = navController,
                      snackbarEvent = favViewMode.snackbarEvent,
                      scrollBehavior = topAppBarScrollBehavior,
                      images = favImages,
                      favoriteImageIDs = favoriteImageIds,


                      onImageClick = { Imageid ->

                          navController.navigate(Routes.FullScreen(imageId = Imageid))
                      },
                      toggleFavoriteStatus = {

                          favViewMode.toggleFavoriteStatus(it)

                      }, onSearchClick = { navController.navigate(Routes.SearchScreen) })
        }

        composable<Routes.FullScreen> { backStackEntry ->

            val ImageId = backStackEntry.toRoute<Routes.FullScreen>().imageId

            val fullScreenViewModel: FullScreenViewModel = hiltViewModel()
            val image = fullScreenViewModel.image
            FullImageScreen(snackbarEvent = fullScreenViewModel.snackbarEvent,
                            snackbarState = snackbarState,
                            navController = navController,
                            image = image,
                            onProfileClick = {
                                navController.navigate(Routes.ProfileScreen(it))
                            },
                            onBackButtonClick = { navController.popBackStack() },
                            onImageDownloadTypeClick = { url, fileName ->
                                fullScreenViewModel.downloadImage(url, fileName)

                            },
                            ImageId = ImageId)

        }

        composable<Routes.ProfileScreen> { NavBackStackEntry ->

            val profileUrlLik = NavBackStackEntry.toRoute<Routes.ProfileScreen>().profileLink

            ProfileScreen(profileLink = profileUrlLik, onBackClick = { navController.navigateUp() })
        }
    }
}