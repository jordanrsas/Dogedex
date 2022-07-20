package com.hackaprende.dogedex.di

import com.hackaprende.dogedex.machinelearning.ClassifierRepository
import com.hackaprende.dogedex.machinelearning.ClassifierTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassifierTasksModule {

    @Binds
    abstract fun bindClassifierTask(
        classifierRepository: ClassifierRepository
    ): ClassifierTasks
}