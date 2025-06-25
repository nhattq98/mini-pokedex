package com.tahn.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tahn.data.local.database.AppDatabase
import com.tahn.data.local.database.entity.toPokemonDomain
import com.tahn.data.remote.mediator.PokemonRemoteMediator
import com.tahn.data.remote.retrofit.PokemonApiServices
import com.tahn.domain.model.Pokemon
import com.tahn.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
internal class PokemonRepositoryImpl(
    private val database: AppDatabase,
    private val api: PokemonApiServices,
) : PokemonRepository {
    override fun getPokemonPaged(): Flow<PagingData<Pokemon>> {
        val pagingSourceFactory = { database.pokemonDao().getAllPokemon() }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PokemonRemoteMediator(database, api),
            pagingSourceFactory = pagingSourceFactory,
        ).flow.map { pagingData -> pagingData.map { it.toPokemonDomain() } }
    }
}
