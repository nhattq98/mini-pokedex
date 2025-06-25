package com.tahn.domain.repository

import androidx.paging.PagingData
import com.tahn.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonPaged(): Flow<PagingData<Pokemon>>
}
