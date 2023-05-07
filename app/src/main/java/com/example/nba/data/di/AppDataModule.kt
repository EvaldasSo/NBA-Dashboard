package com.example.nba.data.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.nba.data.local.database.NbaDatabase
import com.example.nba.data.local.entity.TeamEntity
import com.example.nba.data.remote.NbaApi
import com.example.nba.data.remote.TeamRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton
@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppDataModule {
    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): NbaDatabase {
        return Room.databaseBuilder(
            context,
            NbaDatabase::class.java,
            "nba.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBeerApi(): NbaApi {
        return Retrofit.Builder()
            .baseUrl(NbaApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideBeerPager(nbaDb: NbaDatabase, nbaApi: NbaApi): Pager<Int, TeamEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = TeamRemoteMediator(
                nbaDb = nbaDb,
                nbaApi = nbaApi
            ),
            pagingSourceFactory = {
                nbaDb.dao.pagingSource()
            }
        )
    }
}