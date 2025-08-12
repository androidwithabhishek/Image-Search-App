package com.example.imageapp.utils

import androidx.compose.material3.SnackbarDuration

data class SnackBarEvent(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
                        )