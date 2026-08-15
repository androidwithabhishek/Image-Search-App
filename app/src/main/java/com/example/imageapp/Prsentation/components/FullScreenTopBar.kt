package com.example.imageapp.Prsentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.imageapp.R
import com.example.imageapp.domain.model.DomainUnsplashImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onSearchClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = topAppBarScrollBehavior,

        title = {
            val words = title.trim().split(" ")

            Text(
                text = buildAnnotatedString {

                    words.forEachIndexed { index, word ->

                        withStyle(
                            style = SpanStyle(
                                color = Color.White.copy(
                                    alpha = 0.8f
                                ),
                            )
                        ) {
                            append(word)
                        }

                        if (index < words.lastIndex) {
                            append(" ")
                        }
                    }
                },
                fontSize = 27.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            )
        },

        actions = {

            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(
                            alpha = 0.65f
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.08f
                        ),
                        shape = CircleShape
                    )
                    .clickable {
                        onSearchClick()
                    },
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(22.dp)
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        )
    )
}


@Composable
fun FullScreenTopBar(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onBackButtonClick: () -> Unit,
    onProfileClick: (String) -> Unit,
    onDownloadClick: () -> Unit,
    image: DomainUnsplashImage?
) {
    Box() {

        AnimatedVisibility(
            isVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 35.dp)
            ) {
                IconButton(onClick = {
                    onBackButtonClick()
                }) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null, modifier = Modifier.padding(start = 5.dp)
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                AsyncImage(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    model = image?.photographerImageUrl,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(20.dp))


                Column(modifier = Modifier.clickable {
                    image?.let {
                        onProfileClick(it.photographerProfileFileLink)
                    }

                }) {

                    Text(
                        text = image?.photographerName ?: "",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = image?.photographerUsername ?: "",
                        style = MaterialTheme.typography.bodySmall
                    )

                }



                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { onDownloadClick() }) {

                    Icon(
                        painter = painterResource(R.drawable.baseline_download_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )


                }
                Spacer(modifier = Modifier.width(20.dp))


            }


        }

    }
}