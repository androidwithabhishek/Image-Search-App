package com.example.imageapp.Prsentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.imageapp.Prsentation.components.NetworkStatusBar
import com.example.imageapp.Prsentation.navigarion.Navigation
import com.example.imageapp.domain.model.NetworkStatus
import com.example.imageapp.domain.repository.NetworkConnectivityObserver
import com.example.imageapp.Prsentation.ui.theme.ImageAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    @Inject lateinit var connectivityObserver: NetworkConnectivityObserver
    @OptIn(ExperimentalMaterial3Api::class) override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val status by connectivityObserver.networkStatus.collectAsState()
            val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            val navController: NavHostController = rememberNavController()
            var showStatusBar by rememberSaveable { mutableStateOf(false) }
            var message by rememberSaveable { mutableStateOf("") }
            var bgColors by remember { mutableStateOf(Color.Red) }

            val snackbarState = remember { SnackbarHostState() }

            LaunchedEffect(key1 = status) {
                when (status)
                {


                    NetworkStatus.Connected ->
                    {
                        message = "Connected To Internet 😊😊😊😊"
                        bgColors = Color.Green
                        delay(2000)
                        showStatusBar = false
                    }


                    NetworkStatus.Disconnected ->
                    {

                        showStatusBar = true
                        message = "No Internet Connected 🤬"
                        bgColors = Color.Red

                    }
                }
            }
            ImageAppTheme {
                Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarState) },
                         modifier = Modifier
                             .fillMaxSize()
                             .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                         bottomBar = {
                             NetworkStatusBar(showMessageBar = showStatusBar,
                                              message = message,
                                              backgroundColor = bgColors)
                         }) { innerPadding ->

                    Navigation(navController = navController,
                               modifier = Modifier.padding(innerPadding),

                               topAppBarScrollBehavior = topAppBarScrollBehavior,
                               snackbarState = snackbarState)


//                    image123@
                }
            }
        }
    }
}