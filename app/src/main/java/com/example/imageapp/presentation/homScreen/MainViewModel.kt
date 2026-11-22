package com.example.imageapp.presentation.homScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imageapp.domain.model.DomainUnsplashImage
import com.example.imageapp.domain.model.NetworkStatus
import com.example.imageapp.domain.repository.ImageRepository
import com.example.imageapp.domain.repository.NetworkConnectivityObserver
import com.example.imageapp.utils.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ImageRepository,
    private var connectivityObserver: NetworkConnectivityObserver,
) :
    ViewModel() {


    private var isFeedLoaded = false
    private val _snackBarEvent = Channel<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.receiveAsFlow()

    private val _images = MutableStateFlow(PagingData.empty<DomainUnsplashImage>())
    var images: StateFlow<PagingData<DomainUnsplashImage>> = _images

    init {
        observeConnectivity()
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityObserver.networkStatus.collect {
                status ->
                when (status) {
                    NetworkStatus.Connected -> {

                        if (!isFeedLoaded) {
                            getImages()
                        }
                    }

                    NetworkStatus.Disconnected -> {

                    }
                }
            }
        }
    }


    fun getImages() {
        viewModelScope.launch {
            repository.getEditorialFeedImage().cachedIn(viewModelScope).catch { exception ->
                _snackBarEvent.send(SnackBarEvent("Something went wrong: ${exception.message}"))
            }.collect { pagingData ->
                _images.value = pagingData
                isFeedLoaded = true
            }
        }
    }

    val favoriteImageIds: StateFlow<List<String>> =
        repository.getFavoriteImagesId().catch { exception ->
            _snackBarEvent.send(SnackBarEvent(message = "Something went wrong. ${exception.message}"))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )


    fun toggleFavoriteStatus(image: DomainUnsplashImage) {

        viewModelScope.launch {
            try {

                repository.toggleFavoriteStatus(image)


            } catch (e: Exception) {
                _snackBarEvent.send(SnackBarEvent(message = "Something went wrong ${e.message}"))
            }
        }
    }


}