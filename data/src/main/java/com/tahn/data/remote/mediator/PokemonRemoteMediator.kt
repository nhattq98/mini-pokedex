package com.tahn.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tahn.data.local.database.AppDatabase
import com.tahn.data.local.database.entity.PokemonEntity
import com.tahn.data.remote.retrofit.PokemonApiServices

@OptIn(ExperimentalPagingApi::class)
internal class PokemonRemoteMediator(
    private val database: AppDatabase,
    private val api: PokemonApiServices,
) : RemoteMediator<Int, PokemonEntity>() {
    private val pageSize = 20

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>,
    ): MediatorResult {
        val offset =
            when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.pages.sumOf { it.data.size }
            }

        return try {
            val response = api.getPokemonList(limit = pageSize, offset = offset)
            val entities = response.results.map { it.toEntity() }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.pokemonDao().clearAll()
                }
                database.pokemonDao().insertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = response.results.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
