package com.tahn.minipokedex.di

import com.tahn.domain.usecase.GetPokemonPagingSourceUseCase
import com.tahn.minipokedex.ui.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val useCaseModule =
    module {
        singleOf(::GetPokemonPagingSourceUseCase)
    }

internal val viewModelModule =
    module {
        viewModelOf(::HomeViewModel)
    }
