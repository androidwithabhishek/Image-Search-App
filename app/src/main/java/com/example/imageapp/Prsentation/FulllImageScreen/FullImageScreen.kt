package com.example.imagevista.presentation.full_image_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.animateZoomBy
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.BoxWithConstraints

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.imageapp.Prsentation.components.DownLoadBottomSheet
import com.example.imageapp.Prsentation.components.FullScreenTopBar
import com.example.imageapp.Prsentation.components.ImageDownloadOption
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.utils.SnackBarEvent
import com.example.imageapp.utils.rememberWindowInsetsController
import com.example.imageapp.utils.toggleStatusBars
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.max


@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullImageScreen(

    snackBarHostState: SnackbarHostState,
    snackBarEvent: Flow<SnackBarEvent>,
    imageClass: UnsplashImage?,
    onBackClick: () -> Unit,
    onPhotographerNameClick: (String) -> Unit,
    OnImageDownloadClick: (String, String?) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showBars by rememberSaveable { mutableStateOf(false) }
    val windowInsetsController = rememberWindowInsetsController()
    var isDownloadBottomSheetOpen by remember { mutableStateOf(false) }
    var sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val image = imageClass?.imageUrlRaw
    val imageUrl = imageClass?.imageUrlRegular
    val thumbnailUrl = imageClass?.imageUrlRegular

    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(false)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    val imageRequestThumb = ImageRequest.Builder(context)
        .data(thumbnailUrl)
        .crossfade(false)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    val painter = rememberAsyncImagePainter(model = imageRequest)
    val thumbnailPainter = rememberAsyncImagePainter(model = imageRequestThumb)
    val state by painter.state.collectAsState()
    val thumbnailPainterState by thumbnailPainter.state.collectAsState()

    LaunchedEffect(Unit) {

        windowInsetsController.toggleStatusBars(showBars)
    }
    LaunchedEffect(true) {
        snackBarEvent.collect { event ->
            snackBarHostState.showSnackbar(
                message = event.message,
                duration = event.duration
            )
        }

    }
    BackHandler {
        windowInsetsController.toggleStatusBars(true)
        onBackClick()

    }




    DownLoadBottomSheet(
        onDismissRequest = { isDownloadBottomSheetOpen = false },
        sheetState = sheetState,
        isOpen = isDownloadBottomSheetOpen,
        onLabelClick = { option ->
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    isDownloadBottomSheetOpen = false
                }
            }
            val url = when (option) {
                ImageDownloadOption.SMALL -> imageClass?.imageUrlSmall
                ImageDownloadOption.MEDIUM -> imageClass?.imageUrlRegular
                ImageDownloadOption.ORIGINAL -> imageClass?.imageUrlRaw
            }
            url.let {
                OnImageDownloadClick(it.toString(), imageClass?.description?.take(10).toString())
                Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()
            }
        }

    )












    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {


            var scale by remember { mutableFloatStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }
            val isImageZoomed: Boolean by remember { derivedStateOf { scale != 1f } }
            val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
                scale = max(scale * zoomChange, 1f)
                val maxX = (constraints.maxWidth * (scale - 1)) / 2
                val maxY = (constraints.maxHeight * (scale - 1)) / 2
                offset = Offset(
                    x = (offset.x + offsetChange.x).coerceIn(-maxX, maxX),
                    y = (offset.y + offsetChange.y).coerceIn(-maxY, maxY)
                )
            }

            Image(
                painter = if (state is AsyncImagePainter.State.Loading) thumbnailPainter else painter,
                contentDescription = "Full Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .transformable(transformState)
                    .combinedClickable(
                        onDoubleClick = {
                            if (isImageZoomed) {
                                scale = 1f
                                offset = Offset.Zero
                            } else {
                                scope.launch { transformState.animateZoomBy(zoomFactor = 3f) }
                            }
                        },
                        onClick = {
                            showBars = !showBars
                            windowInsetsController.toggleStatusBars(show = showBars)
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
            )


        }
        if (thumbnailPainterState is AsyncImagePainter.State.Error) {
            CircularProgressIndicator(color = Color.White)
        }

        FullScreenTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 40.dp),
            image = imageClass,
            isVisible = showBars,
            onBackButtonClick = onBackClick,
            onProfileClick = onPhotographerNameClick,
            onDownloadClick = { isDownloadBottomSheetOpen = true }
        )
    }


}

