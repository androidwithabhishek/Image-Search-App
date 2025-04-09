package com.example.imageapp.Prsentation.HomeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageapp.domain.model.NetworkStatus
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.domain.repository.ImageRepository
import com.example.imageapp.domain.repository.NetworkConnectivityObserver
import com.example.imageapp.utils.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ImageRepository,private  var connectivityObserver: NetworkConnectivityObserver) :
    ViewModel()
{
    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    var images: List<UnsplashImage> by mutableStateOf(emptyList())
        private set

    init
    {
        getImages()

        viewModelScope.launch {
            connectivityObserver.networkStatus.collect{
                if (it is NetworkStatus.Connected){
                    getImages()
                }
            }
        }


    }

    private fun getImages()
    {
        viewModelScope.launch {
            try
            {

                    val result = repository.getEditorialFeedImage()

                    images = result



            } catch (e: UnknownHostException)
            {
                _snackbarEvent.trySend(SnackbarEvent(
                        message = "No Internet connection ,Please check your network ${e.message}",

                        ))
            } catch (e: Exception)
            {
                _snackbarEvent.trySend(SnackbarEvent(
                        message = "Chud Gye Guru ${e.message}",

                        ))
            }
        }


    }


}