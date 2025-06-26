package com.tahn.data.di

import com.tahn.data.BuildConfig
import com.tahn.data.helper.BuildFlavor
import com.tahn.data.helper.FlavorHelper
import com.tahn.data.local.database.AppDatabase
import com.tahn.data.local.storage.AppPreferences
import com.tahn.data.local.storage.AppPreferencesImpl
import com.tahn.data.provider.DispatcherProviderImpl
import com.tahn.data.remote.retrofit.helper.NetworkBuilder
import com.tahn.data.remote.retrofit.interceptor.HeaderInterceptor
import com.tahn.data.repository.PokemonRepositoryImpl
import com.tahn.domain.provider.DispatcherProvider
import com.tahn.domain.repository.PokemonRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val dispatcherModule =
    module {
        singleOf(::DispatcherProviderImpl) { bind<DispatcherProvider>() }
    }

internal val repositoryModule =
    module {
        singleOf(::PokemonRepositoryImpl) { bind<PokemonRepository>() }
    }

internal val helperModule =
    module {
        single {
            FlavorHelper(BuildFlavor)
        }
    }

internal val localModule =
    module {
        singleOf(AppDatabase::getInstance)
        single<AppPreferences> {
            AppPreferencesImpl(androidContext(), get())
        }
    }

internal val remoteModule =
    module {
        singleOf(::HeaderInterceptor)
        single {
            NetworkBuilder.buildOkkHttpClient(get(), BuildConfig.DEBUG)
        }
        singleOf(NetworkBuilder::buildService)
    }

val dataModules =
    module {
        includes(dispatcherModule, helperModule, localModule, remoteModule, repositoryModule)
    }
