package com.tahn.domain.usecase

import androidx.paging.PagingData
import com.tahn.domain.model.Pokemon
import com.tahn.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonPagingSourceUseCase(
    private val pokemonRepository: PokemonRepository,
) {
    operator fun invoke(): Flow<PagingData<Pokemon>> = pokemonRepository.getPokemonPaged()
}
