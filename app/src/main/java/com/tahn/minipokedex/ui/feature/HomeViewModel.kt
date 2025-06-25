package com.tahn.minipokedex.ui.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tahn.domain.model.Pokemon
import com.tahn.domain.usecase.GetPokemonPagingSourceUseCase
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val getPagedPokemonUseCase: GetPokemonPagingSourceUseCase,
) : ViewModel() {
    val pokemonFlow: Flow<PagingData<Pokemon>> =
        getPagedPokemonUseCase().cachedIn(viewModelScope)
}
