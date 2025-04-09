package com.example.imageapp.utils

import androidx.compose.material3.SnackbarDuration
import kotlin.time.Duration

data class SnackbarEvent(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
                        )