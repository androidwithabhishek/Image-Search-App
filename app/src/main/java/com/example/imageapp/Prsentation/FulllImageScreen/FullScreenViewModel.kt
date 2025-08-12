package com.example.imageapp.Prsentation.FulllImageScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.imageapp.Prsentation.navigarion.Routes
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.domain.repository.Downloader
import com.example.imageapp.domain.repository.ImageRepository
import com.example.imageapp.utils.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class FullScreenViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val downLoadRepo: Downloader,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _snackBarEvent = Channel<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.receiveAsFlow()


    private val imageId = savedStateHandle.toRoute<Routes.FullScreen>().imageId
    var image: UnsplashImage? by mutableStateOf(null)
        private set

    init {
        getImage()
    }
    fun getImage() {
        viewModelScope.launch {
            try {
                val result = repository.getImage(imageId)
                image = result
            } catch (e: UnknownHostException) {
                _snackBarEvent.send(SnackBarEvent(message = "No Internet connection. Please check you network."))
            } catch (e: Exception) {
                _snackBarEvent.send(SnackBarEvent(message = "Something went wrong: ${e.message}"))
            }
        }

    }

    fun downloadImage(url: String, title: String) {
        viewModelScope.launch {
            try {
                downLoadRepo.downloadFile(url = url, fileName = title)
            } catch (e: Exception) {
                _snackBarEvent.send(
                    SnackBarEvent(
                        message = "Chud Gye Guru ${e.message}",

                        )
                )

            }
        }

    }


}