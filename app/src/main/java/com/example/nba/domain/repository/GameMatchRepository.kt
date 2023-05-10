package com.example.nba.domain.repository

import androidx.paging.PagingData
import com.example.nba.data.local.entity.GameMatchEntity
import kotlinx.coroutines.flow.Flow

interface GameMatchRepository {

    fun getPagingDataGameMatches(gameId: Int): Flow<PagingData<GameMatchEntity>>
}