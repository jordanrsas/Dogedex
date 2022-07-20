package com.hackaprende.dogedex.doglist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(
    private val dogRepository: DogTasks
) : ViewModel() {

    var dogList by mutableStateOf<List<Dog>>(listOf())
        private set

    var status by mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    init {
        getDogCollection()
    }

    private fun getDogCollection() {
        viewModelScope.launch {
            status = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    fun resetApiResponseStatus() {
        status = null
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            dogList = apiResponseStatus.data
        }
        status = apiResponseStatus as ApiResponseStatus<Any>
    }
}