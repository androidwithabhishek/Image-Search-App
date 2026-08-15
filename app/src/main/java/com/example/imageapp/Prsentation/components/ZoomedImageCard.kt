package com.example.imageapp.Prsentation.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.imageapp.domain.model.DomainUnsplashImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer


@Composable
fun ZoomedImageCard(image: DomainUnsplashImage?, isVisibility: Boolean) {

    val context = LocalContext.current

    val imageRequest = remember(image) {
        ImageRequest.Builder(context)
            .data(image?.imageUrlRegular)
            .crossfade(true)
            .build()
    }

    val photographerImageUrlLarge = remember(image) {
        ImageRequest.Builder(context)
            .data(image?.photographerImageUrl?.substringBefore("?"))
            .crossfade(true)
            .build()
    }

    if (isVisibility) {
        Box(modifier = Modifier.fillMaxSize().background(color = Color.Transparent), contentAlignment = Alignment.Center) {


            AnimatedVisibility(
                isVisibility,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Card(
                    modifier = Modifier.padding(16.dp).background(color = Color.Transparent)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth().background(color = Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AsyncImage(
                            model = photographerImageUrlLarge,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(CircleShape)
                                .size(34.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = image?.photographerName ?: "No Name",
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 18.sp
                        )


                    }
                    var isLoading by remember { mutableStateOf(true) }

                    AsyncImage(
                        model = imageRequest,
                        onState = {
                            isLoading = it is AsyncImagePainter.State.Loading
                        },
                        modifier = Modifier.placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.White.copy(
                                    alpha = 0.6f
                                )
                            ),
                            color = Color.Gray.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        ).fillMaxWidth(),
                        contentDescription = null,

                    )
                }
            }
        }

    }


}