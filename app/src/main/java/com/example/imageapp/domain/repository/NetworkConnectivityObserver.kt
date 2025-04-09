package com.example.imageapp.domain.repository

import com.example.imageapp.domain.model.NetworkStatus
import kotlinx.coroutines.flow.StateFlow


interface NetworkConnectivityObserver {


    val networkStatus: StateFlow<NetworkStatus>


}