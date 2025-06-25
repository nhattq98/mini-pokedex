package com.tahn.data.provider

import com.tahn.domain.provider.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class DispatcherProviderImpl : DispatcherProvider {
    override fun io(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun default(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun immediate(): CoroutineDispatcher {
        return Dispatchers.Main.immediate
    }
}