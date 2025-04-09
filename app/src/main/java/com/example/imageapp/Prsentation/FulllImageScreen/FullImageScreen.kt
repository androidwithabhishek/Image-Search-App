package com.example.imagevista.presentation.full_image_screen

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.imageapp.Prsentation.components.DownLoadBottomSheet
import com.example.imageapp.Prsentation.components.FullScreenTopBar
import com.example.imageapp.Prsentation.components.ImageDownloadOption
import com.example.imageapp.Prsentation.components.ImageVistaLoadingBar
import com.example.imageapp.R
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.utils.SnackbarEvent
import com.example.imageapp.utils.rememberWindowInsetsController
import com.example.imageapp.utils.toggleStatusBars
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class) @Composable
fun FullImageScreen(navController: NavHostController,
                    snackbarState: SnackbarHostState,
                    snackbarEvent: Flow<SnackbarEvent>,

                    image: UnsplashImage?,
                    onBackButtonClick: () -> Unit,
                    onProfileClick: (String) -> Unit,
                    ImageId: String,

//                    = {
//
//                        navController.navigate(Routes.ProfileScreen(it))
//
//
//
//                    },
                    onImageDownloadTypeClick: (url: String, fileName: String) -> Unit

                   )
{
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    var showBars by rememberSaveable { mutableStateOf(false) }
    val windowInsetsController = rememberWindowInsetsController()


    var isDownloadBottomSheetOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

        windowInsetsController.toggleStatusBars(showBars)
    }


    BackHandler {
        windowInsetsController.toggleStatusBars(true)
        onBackButtonClick()

    }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collect {
            snackbarState.showSnackbar(message = it.message, duration = it.duration)
        }
    }


    var sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isBotttomSheetOpen by remember { mutableStateOf(false) }

    DownLoadBottomSheet(onDismissRequest = { isBotttomSheetOpen = false },
                        sheetState = sheetState,
                        isOpen = isBotttomSheetOpen,
                        onLabelClick = { option ->


                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible)
                                {
                                    isBotttomSheetOpen = false
                                }
                            }
                            val url = when (option)
                            {
                                ImageDownloadOption.SMALL -> image?.imageUrlSmall
                                ImageDownloadOption.MEDIUM -> image?.imageUrlRegular
                                ImageDownloadOption.ORIGINAL -> image?.imageUrlRaw
                            }
                            url?.let {
                                onImageDownloadTypeClick(it, image?.description?.take(20) ?: "")
//                                Toast.makeText(context, "DownLoading...", Toast.LENGTH_LONG).show()
                            }


                        })


    Box(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(contentAlignment = Alignment.Center) {


            var offset by remember { mutableStateOf(Offset.Zero) }
            var scale by remember { mutableStateOf(1f) }

            val isImageZoomed: Boolean by remember { derivedStateOf { scale != 1f } }


            val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
                scale = max(scale * zoomChange, 1f)
                val maxX = (constraints.maxWidth * (scale - 1)) / 2
                val maxY = (constraints.maxHeight * (scale - 1)) / 2

                offset = Offset(x = (offset.x + offsetChange.x).coerceIn(-maxX, maxX),
                                y = (offset.y + offsetChange.y).coerceIn(-maxY, maxY))
            }
            var isLoading by remember { mutableStateOf(true) }
            var isError by remember { mutableStateOf(false) }


            val imageLoader =
                    rememberAsyncImagePainter(model = image?.imageUrlRaw, onState = { imageState ->
                        isLoading = imageState is AsyncImagePainter.State.Loading
                        isError = imageState is AsyncImagePainter.State.Error
                    })




            if (isLoading)
            {
                ImageVistaLoadingBar()
            }


            Image(painter = if (isError.not()) imageLoader
            else painterResource(id = R.drawable.baseline_error_24),
                  contentDescription = null,
                  modifier = Modifier
                      .fillMaxSize()
                      .transformable(transformState)
                      .combinedClickable(onDoubleClick = {


                          if (isImageZoomed)
                          {
                              scale = 1f
                              offset = Offset.Zero
                          }
                          else
                          {
                              scope.launch { transformState.animateZoomBy(zoomFactor = 3f) }

                          }
                      }, onClick = {

                          showBars = !showBars
                          windowInsetsController.toggleStatusBars(show = showBars)
                      }, indication = null, interactionSource = remember {
                          MutableInteractionSource()


                      })
                      .graphicsLayer(scaleX = scale,
                                     scaleY = scale,
                                     translationX = offset.x,
                                     translationY = offset.y)


                 )


        }

        FullScreenTopBar(modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .padding(16.dp)
            .padding(horizontal = 16.dp),
                         image = image,
                         onProfileClick = onProfileClick,
                         onDownloadClick = { isBotttomSheetOpen = true },
                         onBackButtonClick = onBackButtonClick,
                         isVisible = showBars

                        )

    }


}