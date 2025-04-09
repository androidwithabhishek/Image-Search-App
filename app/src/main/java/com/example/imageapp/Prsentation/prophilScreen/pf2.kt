package com.example.imageapp.Prsentation.prophilScreen

import android.graphics.Color
import android.os.Build
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView



@OptIn(ExperimentalMaterial3Api::class) @Composable
fun ProfileScreen2(
        profileLink: String,
        onBackClick: () -> Unit
                 ) {
    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var webView by remember { mutableStateOf<WebView?>(null) }
    var hasError by remember { mutableStateOf(false) }
    val isDarkTheme = isSystemInDarkTheme()

    // Handle WebView lifecycle
    DisposableEffect(Unit) {
        onDispose {
            webView?.stopLoading()
            webView?.destroy()
        }
    }

    BackHandler {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            onBackClick()
        }
    }

    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
          ) {
        TopAppBar(
                title = { Text("Profile", color = MaterialTheme.colorScheme.onPrimaryContainer) },
                colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                                          ),
                navigationIcon = {
                    IconButton(onClick = {
                        if (webView?.canGoBack() == true) {
                            webView?.goBack()
                        } else {
                            onBackClick()
                        }
                    }) {
                        Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                    }
                }
                 )

        Box(modifier = Modifier.fillMaxSize()) {
            if (!hasError) {
                AndroidView(
                        factory = { context ->
                            try {
                                WebView(context).apply {
                                    settings.apply {
                                        javaScriptEnabled = true
                                        domStorageEnabled = true
                                        setSupportZoom(true)

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                            forceDark = if (isDarkTheme) {
                                                WebSettings.FORCE_DARK_ON
                                            } else {
                                                WebSettings.FORCE_DARK_OFF
                                            }
                                        }
                                    }

                                    setBackgroundColor(Color.TRANSPARENT)
                                    webViewClient = object : WebViewClient() {
                                        override fun onPageFinished(view: WebView?, url: String?) {
                                            isLoading = false
                                            if (isDarkTheme) {
                                                view?.evaluateJavascript(
                                                        """
                                                document.body.style.backgroundColor='#121212';
                                                document.body.style.color='#ffffff';
                                                """.trimIndent(),
                                                        null
                                                                        )
                                            }
                                        }

                                        override fun onReceivedError(
                                                view: WebView?,
                                                errorCode: Int,
                                                description: String?,
                                                failingUrl: String?
                                                                    ) {
                                            super.onReceivedError(view, errorCode, description, failingUrl)
                                            hasError = true
                                            isLoading = false
                                        }
                                    }

                                    try {
                                        loadUrl(profileLink)
                                        webView = this
                                    } catch (e: Exception) {
                                        hasError = true
                                        isLoading = false
                                    }
                                }
                            } catch (e: Exception) {
                                hasError = true
                                isLoading = false
                                // Return empty view as placeholder
                                WebView(context)
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                           )
            }

            if (isLoading) {
                CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                                         )
            }

            if (hasError) {
                Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                      ) {
                    Text(
                            text = "Failed to load content",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        hasError = false
                        isLoading = true
                    }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}