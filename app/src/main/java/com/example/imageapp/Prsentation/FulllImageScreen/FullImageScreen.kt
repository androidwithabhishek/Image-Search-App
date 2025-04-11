package com.example.imagevista.presentation.full_image_screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.imageLoader
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import coil3.util.Logger
import com.example.imageapp.R
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.utils.SnackbarEvent
import kotlinx.coroutines.flow.Flow


fun preloadImage(context: Context, url: String) {
    val request = ImageRequest.Builder(context)
        .data(url)
        .size(Size.ORIGINAL)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    context.imageLoader.enqueue(request)
}
@Composable
fun FullImageScreen(
    selectedIndex: Int,
    images: LazyPagingItems<UnsplashImage>,
    onBackButtonClick: () -> Unit,
    snackbarState: SnackbarHostState,
    snackbarEvent: Flow<SnackbarEvent>,
    onImageDownloadTypeClick: (url: String, fileName: String) -> Unit,
    onProfileClick: (String) -> Unit
) {
    val pagerState = rememberPagerState(
            initialPage = selectedIndex,
            pageCount = { images.itemCount }
    )

    val context = LocalContext.current
    LaunchedEffect(pagerState.currentPage) {
        val preloadRange = (pagerState.currentPage - 10)..(pagerState.currentPage + 10)
        preloadRange.forEach { index ->
            if (index in 0 until images.itemCount) {
                val image = images[index]
                if (image != null) {
                    preloadImage(context, image.imageUrlRaw)
                }
            }
        }
    }


    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        val image = images[page]

        if (image != null) {


            Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
            ) {
                CenteredFullImage(
                        imageUrl = image.imageUrlRaw,
                )
            }
        }
    }
}



@Composable
fun CenteredFullImage(imageUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // ✅ Use remember to avoid rebuilding ImageRequest on every recomposition
    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .size(Size.ORIGINAL)
            .build()
    }

    // ✅ Load image using the same request (preloading-compatible)
    val painter = rememberAsyncImagePainter(model = imageRequest)
    val state by painter.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
                painter = painter,
                contentDescription = "Full Image",
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
        )

        // ✅ Optional loading indicator
        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}