package com.example.imageapp.Prsentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class) @Composable fun ProTooBar(modifier: Modifier = Modifier,onBackClick: () -> Unit)

{
    TopAppBar(title = {
        Text(text = "Profile")
    }, navigationIcon = {


        IconButton(onClick = {
            onBackClick()
        }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }


    })
}