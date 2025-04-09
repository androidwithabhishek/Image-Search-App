package com.example.imageapp.Prsentation.prophilScreen

import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
        profileLink: String,
        onBackClick: () -> Unit
                 ) {
    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(true) }
    val webView = remember { mutableStateOf<WebView?>(null) }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
                title = { Text(text = "Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                    }
                }
                 )

        BackHandler {
            if (webView.value?.canGoBack() == true) {
                webView.value?.goBack()  // Navigate back in WebView
            } else {
                onBackClick()  // Exit screen if no more history
            }
        }

        Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
           ) {
            AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true

                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    isLoading = false
                                }

                                // Keep navigation within WebView
                                override fun shouldOverrideUrlLoading(
                                        view: WebView,
                                        request: WebResourceRequest
                                                                     ): Boolean {
                                    view.loadUrl(request.url.toString())
                                    return true
                                }
                            }

                            webChromeClient = WebChromeClient()
                            loadUrl(profileLink)
                            webView.value = this
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                       )

            if (isLoading) {
                CircularProgressIndicator()
            }
    }
}}