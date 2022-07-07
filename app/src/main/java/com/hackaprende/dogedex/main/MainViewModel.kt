package com.hackaprende.dogedex.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.doglist.DogRepository
import com.hackaprende.dogedex.machinelearning.Classifier
import com.hackaprende.dogedex.machinelearning.ClassifierRepository
import com.hackaprende.dogedex.machinelearning.DogRecognition
import com.hackaprende.dogedex.model.Dog
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer

class MainViewModel : ViewModel() {

    private val dogRepository = DogRepository()

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog>
        get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>?>(null)
    val status: LiveData<ApiResponseStatus<Dog>?>
        get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition>
        get() = _dogRecognition

    private lateinit var classifierRepository: ClassifierRepository

    fun setUpClassifier(tfLiteModel: MappedByteBuffer, labels: List<String>) {
        val classifier = Classifier(tfLiteModel, labels)
        classifierRepository = ClassifierRepository(classifier)
    }

    fun recognizeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _dogRecognition.value = classifierRepository.recogniezeImage(imageProxy)
            imageProxy.close()
        }
    }

    fun getDogByMlId(mlDogId: String) {
        viewModelScope.launch {
            handleResponseStatus(dogRepository.getDogByMlId(mlDogId))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<Dog>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dog.value = apiResponseStatus.data
        }
        _status.value = apiResponseStatus
    }
}