package com.example.imageapp.Prsentation.FulllImageScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.imageapp.Prsentation.navigarion.Routes
import com.example.imageapp.domain.model.NetworkStatus
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.domain.repository.Downloader
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
class FullScreenViewModel @Inject constructor(private val repository: ImageRepository,
                                              private val downLoadRepo: Downloader,
                                              private  var connectivityObserver:
                                              NetworkConnectivityObserver,
                                              savedStateHandle: SavedStateHandle) : ViewModel()
{

    var image: UnsplashImage? by mutableStateOf(null)
        private set

    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    private val imageId = savedStateHandle.toRoute<Routes.FullScreen>().imageId

    init
    {
        getImage()

        viewModelScope.launch {
            connectivityObserver.networkStatus.collect{
                if (it is NetworkStatus.Connected){
                    getImage()
                }
            }
        }

    }

    private fun getImage()
    {
        viewModelScope.launch {
            try
            {
                val result = repository.getImage(imageId = imageId)
                image = result
            }
            catch (e:UnknownHostException){
                _snackbarEvent.send(SnackbarEvent(
                        message = "No Internet connection ,Please check your network ${e.message}",

                        ))
            }
            catch (e: Exception)
            {
                _snackbarEvent.send(SnackbarEvent(
                        message = "No Internet connection ${e.message}",

                        ))
            }
        }
    }


    fun downloadImage(url: String, title: String)
    {
        viewModelScope.launch {
            try
            {
                downLoadRepo.downloadFile(url = url, fileName = title)
            } catch (e: Exception)
            {
                _snackbarEvent.send(SnackbarEvent(
                        message = "Chud Gye Guru ${e.message}",

                        ))

            }
        }

    }


}