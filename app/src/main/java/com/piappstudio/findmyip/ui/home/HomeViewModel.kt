package com.piappstudio.findmyip.ui.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piappstudio.findmyip.data.IpDetail
import com.piappstudio.findmyip.network.IPRepository
import com.piappstudio.findmyip.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeViewState(val status:Resource.Status = Resource.Status.NONE, val ipDetail: IpDetail? = null)
@HiltViewModel
class HomeViewModel @Inject constructor(val repository:IPRepository):ViewModel() {

    private val _mutHomeViewState = MutableStateFlow(HomeViewState())
    val homeViewState:StateFlow<HomeViewState> = _mutHomeViewState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getIPAddress().collect { resource->
                _mutHomeViewState.update { state->
                    state.copy(status =  resource.status, ipDetail =  resource.data)
                }
            }
        }
    }

}