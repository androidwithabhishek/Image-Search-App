package com.example.imagevista.presentation.full_image_screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.imageLoader
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.utils.SnackbarEvent
import kotlinx.coroutines.flow.Flow
import kotlin.math.absoluteValue


//fun preloadImage(context: Context, url: String)
//{
//    val request = ImageRequest.Builder(context).data(url).size(Size.ORIGINAL)
//        .diskCachePolicy(CachePolicy.ENABLED).memoryCachePolicy(CachePolicy.ENABLED).build()
//
//    context.imageLoader.enqueue(request)
//}

@Composable
fun FullImageScreen(
    selectedIndex: Int,
    images: LazyPagingItems<UnsplashImage> ,
    onBackButtonClick: () -> Unit,
    snackbarState: SnackbarHostState,
    snackbarEvent: Flow<SnackbarEvent>,
    onImageDownloadTypeClick: (url: String, fileName: String) -> Unit,
    onProfileClick: (String) -> Unit,
)
{


    val pagerState =
            rememberPagerState(initialPage = selectedIndex, pageCount = { images.itemCount })

    val context = LocalContext.current






    HorizontalPager(state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)) { page ->
//        val image = images [page]

         val isCurrentPage = pagerState.currentPage == page
            val transitionProgress = pagerState.currentPageOffsetFraction.absoluteValue

            Box(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    // Scale or translate only during transition
                    val scale = if (isCurrentPage) 1f - (transitionProgress * 0.05f) else 0.95f
                    scaleX = scale
                    scaleY = scale
                }, contentAlignment = Alignment.Center) {
                CenteredFullImage(
                    images = images, page = page )
                Spacer(modifier = Modifier.width(10.dp))
            }


    }
}


@Composable
fun CenteredFullImage( modifier: Modifier = Modifier,
                      images: LazyPagingItems<UnsplashImage>,page:Int)
{

     val image = images[page]
    val imageUrl = image?.imageUrlRegular
    val thumbnailUrl = image?.imageUrlRegular

    val context = LocalContext.current


    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(false)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    val imageRequestThumb = ImageRequest.Builder(context)
        .data(thumbnailUrl)
        .crossfade(false)
//        .diskCachePolicy(CachePolicy.ENABLED)
//        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

//    context.imageLoader.enqueue(imageRequestThumb)


    LaunchedEffect(page) {
        val preloadRange = (page  - 2)..(page + 30)
        preloadRange.forEach { index ->
            if (index in 0 until images.itemCount)
            {
                val image = images[index]


                if (image != null)
                {




                    context.imageLoader.enqueue(imageRequest)




                }
            }
        }


    }












    val painter = rememberAsyncImagePainter(model=imageRequest)
    val thumbnailPainter =rememberAsyncImagePainter(model = imageRequestThumb)



        val state by painter.state.collectAsState()







    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(painter = if (state is AsyncImagePainter.State.Success) painter else thumbnailPainter,
              contentDescription = "Full Image",
              contentScale = ContentScale.Fit,
              modifier = modifier
                  .fillMaxSize()
                  .clip(RoundedCornerShape(10.dp)))

        // ✅ Optional loading indicator
        if (state is AsyncImagePainter.State.Loading)
        {
            CircularProgressIndicator(color = Color.White)
        }
    }
}