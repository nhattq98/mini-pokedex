package com.tahn.domain.provider

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun io(): CoroutineDispatcher

    fun default(): CoroutineDispatcher

    fun main(): CoroutineDispatcher

    fun immediate(): CoroutineDispatcher
}
