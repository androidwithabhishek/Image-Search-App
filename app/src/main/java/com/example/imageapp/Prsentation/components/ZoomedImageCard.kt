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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.imageapp.domain.model.UnsplashImage
import com.skydoves.cloudy.cloudy


@Composable fun ZoomedImageCard(images: UnsplashImage?, isVisibility: Boolean)
{
    val imageRequest =
            ImageRequest.Builder(LocalContext.current).data(images?.imageUrlRaw).crossfade(true)
                .build()
    if (isVisibility)
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            // Background with blur effect
            Box(modifier = Modifier
                .cloudy(radius = 25)
                .fillMaxSize()

                // Official Blur Modifier
                .background(Color.Black.copy(alpha = 0.8f)) // Optional: Dim effect
            )

            // Card with Image (No blur applied)


            AnimatedVisibility(isVisibility,
                               enter = scaleIn() + fadeIn(),
                               exit = scaleOut() + fadeOut()) {
                Card(modifier = Modifier.padding(16.dp) // Optional for spacing
                ) {

                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {


                        val photographerImageUrlLarge: String? =
                                images?.photographerImageUrl?.substringBefore("?")
                        AsyncImage(model = photographerImageUrlLarge,
                                   contentDescription = null,

                                   modifier = Modifier
                                       .padding(10.dp)
                                       .clip(CircleShape)
                                       .size(34.dp),
                                   contentScale = ContentScale.Crop)
                        Text(text = images?.photographerName ?: "No Name",
                             style = MaterialTheme.typography.labelLarge,
                             fontSize = 18.sp)


                    }
                    AsyncImage(model = imageRequest,
                               contentDescription = null,
                               modifier = Modifier.fillMaxWidth())
                }
            }
        }

    }


}