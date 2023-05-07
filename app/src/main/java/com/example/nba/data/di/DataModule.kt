package com.example.nba.data.di

import com.example.nba.data.repository.UserDataRepositoryImpl
import com.example.nba.domain.repository.UserDataRepository
import com.example.nba.data.util.ConnectivityManagerNetworkMonitor
import com.example.nba.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl,
    ): UserDataRepository
}