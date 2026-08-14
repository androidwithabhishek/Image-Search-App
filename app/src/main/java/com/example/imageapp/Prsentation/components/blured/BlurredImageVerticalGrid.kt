package com.example.imageapp.Prsentation.components.blured

import com.example.imageapp.Prsentation.components.ImageCard



import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.imageapp.Prsentation.components.blured.BluerImageCard
import com.example.imageapp.domain.model.UnsplashImage


@Composable
fun BlurredImageVerticalGrid(
    modifier: Modifier = Modifier,
    images: LazyPagingItems<UnsplashImage>,
    onImageClick: (imageId: String, index: Int) -> Unit,
    onImageDragStart: (UnsplashImage?) -> Unit,
    onImageDragEnd: () -> Unit,
    onFevClick: (UnsplashImage) -> Unit,
    isFev: Boolean = true,
    favoriteImageIDs: List<String>,


    ) {

    val scrollState = rememberLazyStaggeredGridState()

    Box(modifier = Modifier.fillMaxSize()) {

        LazyVerticalStaggeredGrid(
            state = scrollState,
            columns = StaggeredGridCells.Adaptive(120.dp),
            contentPadding = PaddingValues(10.dp),
            verticalItemSpacing = 10.dp,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.blur(100.dp)
        ) {
            items(count = images.itemCount) { index: Int ->
                val image = images[index]
                BluerImageCard(
                    image = image, modifier = Modifier
                        .clickable {

                            image?.id?.let { imageId ->
                                onImageClick(imageId, index)
                            }
                        }
                        .pointerInput(Unit) {
                            detectDragGesturesAfterLongPress(
                                onDragStart = { onImageDragStart(image) },
                                onDragCancel = { onImageDragEnd() },
                                onDragEnd = { onImageDragEnd() },
                                onDrag = { _, _ -> })
                        }, onFevClick = {
                        image?.let {
                            onFevClick(it)
                        }
                    }, isFev = favoriteImageIDs.contains(image?.id)
                )

            }


        }

    }


}