package com.example.imageapp.Prsentation.HomeScreen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.imageapp.Prsentation.components.HomeTopAppBar
import com.example.imageapp.Prsentation.components.ImageVerticalGrid
import com.example.imageapp.Prsentation.components.ZoomedImageCard
import com.example.imageapp.Prsentation.components.blured.BlurredImageVerticalGrid
import com.example.imageapp.R
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.utils.SnackBarEvent
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState,

    snackbarEvent: Flow<SnackBarEvent>,
    scrollBehavior: TopAppBarScrollBehavior,
    images: LazyPagingItems<UnsplashImage>,
    favoriteImageIDs: List<String>,
    onImageClick: (String, index: Int) -> Unit,
    toggleFavoriteStatus: (UnsplashImage) -> Unit,
    onSearchClick: () -> Unit,
    onFABClick: () -> Unit,


    ) {

    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }


    LaunchedEffect(key1 = true) {
        snackbarEvent.collect { event ->
            snackbarHostState.showSnackbar(message = event.message, duration = event.duration)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {


        BlurredImageVerticalGrid(
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


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            HomeTopAppBar(
                topAppBarScrollBehavior = scrollBehavior,
                title = "ImageApp",
                onSearchClick = onSearchClick
            )

            val gridState = rememberLazyGridState()


            ImageVerticalGrid(
                modifier = Modifier,
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
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd).padding(end = 20.dp, bottom = 20.dp)
                .padding(24.dp),
            onClick = { onFABClick() },
            containerColor = Color.White.copy(alpha = 0.55f),
            contentColor = Color.White.copy(alpha = 0.55f),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 2.dp
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_save_24),
                contentDescription = "Favorites",
                tint = Color.Black
            )
        }
        ZoomedImageCard(image = activeImage, isVisibility = showImagePreview)
    }
}