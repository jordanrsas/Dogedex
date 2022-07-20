package com.hackaprende.dogedex.doglist

import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.ApiService
import com.hackaprende.dogedex.api.dto.AddDogToUserDTO
import com.hackaprende.dogedex.api.dto.DogDTOMapper
import com.hackaprende.dogedex.api.makeNetworkCall
import com.hackaprende.dogedex.di.DispatchersModule
import com.hackaprende.dogedex.model.Dog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DogTasks {
    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>>
    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any>
    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog>
}

class DogRepository @Inject constructor(
    private val apiService: ApiService,
    @DispatchersModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : DogTasks {

    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(dispatcher) {
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            if (allDogsListResponse is ApiResponseStatus.Error) {
                allDogsListResponse
            } else if (userDogsListResponse is ApiResponseStatus.Error) {
                userDogsListResponse
            } else if (allDogsListResponse is ApiResponseStatus.Success &&
                userDogsListResponse is ApiResponseStatus.Success
            ) {
                ApiResponseStatus.Success(
                    getCollectionList(
                        allDogsListResponse.data,
                        userDogsListResponse.data
                    )
                )
            } else {
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>) =
        allDogList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(
                    it.id, it.index, "", "", "", "",
                    "", "", "", "", "",
                    inCollection = false
                )
            }
        }.sorted()

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = apiService.getUserDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = apiService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = apiService.addDogToUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

    override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> = makeNetworkCall {
        val response = apiService.getDogByMlId(mlDogId)

        if (!response.isSuccess) {
            throw Exception(response.message)
        }

        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOToDogDomain(response.data.dog)
    }
}