package com.example.imageapp.Prsentation.components

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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.imageapp.domain.model.DomainUnsplashImage


@Composable
fun ImageVerticalGrid(
    modifier: Modifier = Modifier,
    images: LazyPagingItems<DomainUnsplashImage>,
    onImageClick: (imageId: String, index: Int) -> Unit,
    onImageDragStart: (DomainUnsplashImage?) -> Unit,
    onImageDragEnd: () -> Unit,
    onFevClick: (DomainUnsplashImage) -> Unit,
    isFev: Boolean = true,
    favoriteImageIDs: List<String>,


    ) {

    val scrollState = rememberLazyStaggeredGridState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier,
            state = scrollState,
            columns = StaggeredGridCells.Adaptive(120.dp),
            contentPadding = PaddingValues(10.dp),
            verticalItemSpacing = 10.dp,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(count = images.itemCount) { index: Int ->
                val image = images[index]
                ImageCard(
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