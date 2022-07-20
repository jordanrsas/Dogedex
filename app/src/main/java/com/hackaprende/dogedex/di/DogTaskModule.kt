package com.hackaprende.dogedex.di

import com.hackaprende.dogedex.doglist.DogRepository
import com.hackaprende.dogedex.doglist.DogTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DogTaskModule {
    @Binds
    abstract fun bindDogTasks(
        dogRepository: DogRepository
    ): DogTasks
}