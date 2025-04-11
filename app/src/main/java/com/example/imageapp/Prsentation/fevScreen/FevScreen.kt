package com.example.imageapp.Prsentation.fevScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun FevScreen(modifier: Modifier = Modifier,
              snackbarHostState: SnackbarHostState,
              navHostController: NavHostController,
              snackbarEvent: Flow<SnackbarEvent>,
              scrollBehavior: TopAppBarScrollBehavior,
              images: LazyPagingItems<UnsplashImage>,
              favoriteImageIDs: List<String>,
              onBackClick: () -> Unit = { navHostController.navigateUp() },

              onImageClick: (String,Int) -> Unit,
              toggleFavoriteStatus: (UnsplashImage) -> Unit,
              onSearchClick: () -> Unit)
{


    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }


    LaunchedEffect(key1 = true) {
        snackbarEvent.collect { event ->
            snackbarHostState.showSnackbar(message = event.message, duration = event.duration)
        }
    }


Column {


        HomeTopAppBar(TopAppBarScrollBehavior = scrollBehavior,
                      title = "Favorite",
                      onSearchClick = onSearchClick)
//
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

    ZoomedImageCard(images = activeImage, isVisibility = showImagePreview)

    if (images.itemCount == 0)
    {
        EmptyState(modifier=Modifier
            .fillMaxSize()
            .padding(16.dp))
    }


}


@Composable
private fun EmptyState(modifier: Modifier = Modifier)
{
    Column(modifier = modifier,
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(modifier = Modifier.fillMaxWidth(),
             painter = painterResource(id = R.drawable.baseline_hourglass_empty_24),
             contentDescription = null)
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = "No Saved Images",
             modifier = Modifier.fillMaxWidth(),
             textAlign = TextAlign.Center,
             style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Images you save will be stored here",
             modifier = Modifier.fillMaxWidth(),
             textAlign = TextAlign.Center,
             style = MaterialTheme.typography.bodyMedium)
    }
}