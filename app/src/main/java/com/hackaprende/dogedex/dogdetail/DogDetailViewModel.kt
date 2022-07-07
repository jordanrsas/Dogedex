package com.hackaprende.dogedex.dogdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.doglist.DogRepository
import kotlinx.coroutines.launch

class DogDetailViewModel : ViewModel() {

    private val dogRepository = DogRepository()

    private val _status = MutableLiveData<ApiResponseStatus<Any>?>(null)
    val status: LiveData<ApiResponseStatus<Any>?>
        get() = _status


    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        _status.value = apiResponseStatus
    }
}