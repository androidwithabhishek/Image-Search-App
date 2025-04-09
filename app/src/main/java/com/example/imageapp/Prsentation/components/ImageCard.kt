package com.example.imageapp.Prsentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.imageapp.domain.model.UnsplashImage

@Composable fun ImageCard(modifier: Modifier = Modifier,
                          image: UnsplashImage?,
                          onFevClick: () -> Unit,
                          isFev: Boolean)
{

    val imageRequest =
            ImageRequest.Builder(LocalContext.current).data(image?.imageUrlSmall).crossfade(true)
                .build()


    val aspectRatio: Float by remember {
        derivedStateOf {
            (image?.width?.toFloat() ?: 1f) / (image?.height?.toFloat() ?: 1f)
        }
    }


    Card(shape = RoundedCornerShape(10.dp),
         modifier = Modifier
             .fillMaxSize()
             .aspectRatio(aspectRatio)
             .then(modifier)) {

        Box{
            AsyncImage(model = imageRequest,
                       contentDescription = null,
                       modifier = Modifier.fillMaxSize(),
                       contentScale = ContentScale.FillBounds)

            FavButton(
                    isFev = isFev,
                    onFevClick = onFevClick,
                    modifier = Modifier.align(Alignment.BottomEnd)
            )

        }

    }
}



@Composable
fun FavButton(
    modifier: Modifier = Modifier,
    isFev: Boolean,
    onFevClick: () -> Unit
) {
    val transition = updateTransition(targetState = isFev, label = "favoriteTransition")
    val tint by transition.animateColor(label = "color") { isFavorite ->
        if (isFavorite) Color.Red else Color.Gray
    }
    val size by transition.animateDp(label = "size") { isFavorite ->
        if (isFavorite) 24.dp else 24.dp
    }

    IconButton(
            modifier = modifier,
            onClick = onFevClick
    ) {
        Icon(
                imageVector = if (isFev) Icons.Outlined.FavoriteBorder else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(size)
        )
    }
}