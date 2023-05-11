package com.example.nba.data.di

import android.content.Context
import androidx.room.Room
import com.example.nba.data.local.database.NbaDatabase
import com.example.nba.data.remote.NbaApi
import com.example.nba.data.repository.GameMatchRepositoryImpl
import com.example.nba.data.repository.TeamRepositoryImpl
import com.example.nba.data.util.Constants.BASE_URL
import com.example.nba.domain.repository.GameMatchRepository
import com.example.nba.domain.repository.TeamRepository
import com.example.nba.domain.use_case.game_match.GetMatchGamesUseCase
import com.example.nba.domain.use_case.team.GetTeamByIdUseCase
import com.example.nba.domain.use_case.game_match.GameMatchUseCases
import com.example.nba.domain.use_case.team.GetPlayerByNameUseCase
import com.example.nba.domain.use_case.team.GetTeamsUseCase
import com.example.nba.domain.use_case.team.TeamUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDataModule {
    @Provides
    @Singleton
    fun provideNbaDatabase(@ApplicationContext context: Context): NbaDatabase {
        return Room.databaseBuilder(
            context,
            NbaDatabase::class.java,
            "nba.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideNbaApi(okHttpClient: OkHttpClient): NbaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NbaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGameMatchRepository(
        nbaDb: NbaDatabase,
        nbaApi: NbaApi
    ): GameMatchRepository =
        GameMatchRepositoryImpl(
            nbaDb = nbaDb,
            nbaApi = nbaApi
        )

    @Provides
    @Singleton
    fun provideNbaUseCases(gameMatchRepository: GameMatchRepository) = GameMatchUseCases(
        getMatchGamesUseCase = GetMatchGamesUseCase(matchGameMatchRepository = gameMatchRepository),
    )

    @Provides
    @Singleton
    fun provideTeamRepository(
        nbaDb: NbaDatabase,
        nbaApi: NbaApi
    ): TeamRepository =
        TeamRepositoryImpl(
            nbaDb = nbaDb,
            nbaApi = nbaApi,
        )

    @Provides
    @Singleton
    fun provideTeamUseCases(teamRepository: TeamRepository) = TeamUseCases(
        getTeamByIdUseCase = GetTeamByIdUseCase(teamRepository = teamRepository),
        getTeamsUseCase =  GetTeamsUseCase(teamRepository = teamRepository),
        getPlayerByNameUseCase = GetPlayerByNameUseCase(teamRepository = teamRepository)
    )


}