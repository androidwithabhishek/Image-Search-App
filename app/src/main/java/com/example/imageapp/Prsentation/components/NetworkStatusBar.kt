package com.example.imageapp.Prsentation.components



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NetworkStatusBar(
        modifier: Modifier = Modifier,
        showMessageBar: Boolean,
        message: String,
        backgroundColor: Color
                    ) {
    AnimatedVisibility(
            modifier = modifier,
            visible = showMessageBar,
            enter = slideInVertically(animationSpec = tween(durationMillis = 600)) { h -> h },
            exit = slideOutVertically(animationSpec = tween(durationMillis = 600)) { h -> h }
                      ) {
        Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
           ) {
            Text(
                    text = message,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
        }
    }
}