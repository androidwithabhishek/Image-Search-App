package com.example.imageapp.Prsentation.FulllImageScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageapp.domain.repository.Downloader
import com.example.imageapp.domain.repository.ImageRepository
import com.example.imageapp.utils.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullScreenViewModel @Inject constructor(private val repository: ImageRepository,
                                              private val downLoadRepo: Downloader,
                                              ) : ViewModel()
{


    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()


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