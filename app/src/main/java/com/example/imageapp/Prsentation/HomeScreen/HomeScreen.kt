package com.example.imageapp.Prsentation.HomeScreen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.example.imageapp.Prsentation.components.HomeTopAppBar
import com.example.imageapp.Prsentation.components.ImageVerticalGrid
import com.example.imageapp.Prsentation.components.ZoomedImageCard
import com.example.imageapp.R
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.utils.SnackbarEvent
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState,

    snackbarEvent: Flow<SnackbarEvent>,
    scrollBehavior: TopAppBarScrollBehavior,
    images: LazyPagingItems<UnsplashImage>,
    favoriteImageIDs: List<String>,
    onImageClick: (String,index:Int) -> Unit,
    toggleFavoriteStatus: (UnsplashImage) -> Unit,
    onSearchClick: () -> Unit,
    onFABClick: () -> Unit,


    )
{

    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }


    LaunchedEffect(key1 = true) {
        snackbarEvent.collect { event ->
            snackbarHostState.showSnackbar(message = event.message, duration = event.duration)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
               horizontalAlignment = Alignment.CenterHorizontally) {


            HomeTopAppBar(TopAppBarScrollBehavior = scrollBehavior,
                          title = "ImageApp",
                          onSearchClick = onSearchClick)


            ImageVerticalGrid(
                    images = images,
                    onImageClick = onImageClick,
                    favoriteImageIDs = favoriteImageIDs,

                    onImageDragStart = { image ->
                        activeImage = image
                        showImagePreview = true
                    },
                    onImageDragEnd = { showImagePreview = false },

                    onFevClick = { toggleFavoriteStatus(it) },
                    isFev = false,
            )


        }
        FloatingActionButton(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(24.dp),
                             onClick = { onFABClick() }) {
            Icon(painter = painterResource(R.drawable.baseline_save_24),
                 contentDescription = "Favorites",
                 tint = MaterialTheme.colorScheme.onBackground)
        }
        ZoomedImageCard(images = activeImage, isVisibility = showImagePreview)
    }
}