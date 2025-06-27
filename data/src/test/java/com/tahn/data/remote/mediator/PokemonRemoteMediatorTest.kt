package com.tahn.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tahn.data.local.database.AppDatabase
import com.tahn.data.local.database.dao.PokemonDao
import com.tahn.data.local.database.entity.PokemonEntity
import com.tahn.data.remote.response.PokemonDto
import com.tahn.data.remote.response.PokemonListResponse
import com.tahn.data.remote.retrofit.PokemonApiServices
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class PokemonRemoteMediatorTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var database: AppDatabase
    private lateinit var dao: PokemonDao
    private lateinit var api: PokemonApiServices
    private lateinit var remoteMediator: PokemonRemoteMediator

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )

        Dispatchers.setMain(testDispatcher)

        api = mockk()
        dao = mockk(relaxed = true)
        database = mockk()

        val transactionLambda = slot<suspend () -> Unit>()
        coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }


        every { database.pokemonDao() } returns dao
        remoteMediator = PokemonRemoteMediator(database, api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load REFRESH - success and not end of pagination`() =
        runTest {
            // Arrange
            val response =
                PokemonListResponse(
                    count = 20,
                    next = "nextUrl",
                    previous = null,
                    results =
                        listOf(
                            PokemonDto(
                                name = "bulbasaur",
                                url = "https://pokeapi.co/api/v2/pokemon/1/",
                            ),
                        ),
                )

            coEvery { api.getPokemonList(limit = any(), offset = any()) } returns response
            coEvery { dao.clearAll() } just Runs
            coEvery { dao.insertAll(any()) } just Runs

            val pagingState =
                PagingState<Int, PokemonEntity>(
                    pages = listOf(),
                    anchorPosition = null,
                    config = PagingConfig(pageSize = 20),
                    leadingPlaceholderCount = 0,
                )

            // Act
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)

            // Assert
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

            coVerify { dao.clearAll() }
            coVerify { dao.insertAll(any()) }
        }

    @Test
    fun `load PREPEND - returns endOfPaginationReached true`() =
        runTest {
            // Arrange
            val state =
                PagingState<Int, PokemonEntity>(
                    pages = listOf(),
                    anchorPosition = null,
                    config = PagingConfig(pageSize = 20),
                    leadingPlaceholderCount = 0,
                )

            // Act
            val result = remoteMediator.load(LoadType.PREPEND, state)

            // Assert
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `load REFRESH - API throws exception returns MediatorResult Error`() =
        runTest {
            // Arrange
            coEvery { api.getPokemonList(any(), any()) } throws RuntimeException("API error")

            val state =
                PagingState<Int, PokemonEntity>(
                    pages = listOf(),
                    anchorPosition = null,
                    config = PagingConfig(pageSize = 20),
                    leadingPlaceholderCount = 0,
                )

            // Act
            val result = remoteMediator.load(LoadType.REFRESH, state)

            // Assert
            assertTrue(result is RemoteMediator.MediatorResult.Error)
        }

    @Test
    fun `load APPEND - success and not end of pagination`() =
        runTest {
            // Arrange
            val response =
                PokemonListResponse(
                    count = 20,
                    next = "nextUrl",
                    previous = null,
                    results =
                        listOf(
                            PokemonDto(
                                name = "charmander",
                                url = "https://pokeapi.co/api/v2/pokemon/4/",
                            ),
                        ),
                )

            coEvery { api.getPokemonList(limit = any(), offset = any()) } returns response
            coEvery { dao.insertAll(any()) } just Runs

            val pagingState =
                PagingState<Int, PokemonEntity>(
                    pages =
                        listOf(
                            androidx.paging.PagingSource.LoadResult.Page(
                                data =
                                    listOf(
                                        PokemonEntity(
                                            id = 1,
                                            name = "bulbasaur",
                                            imageUrl = "https://pokeapi.co/api/v2/pokemon/1/",
                                        ),
                                    ),
                                prevKey = null,
                                nextKey = 20,
                            ),
                        ),
                    anchorPosition = null,
                    config = PagingConfig(pageSize = 20),
                    leadingPlaceholderCount = 0,
                )

            // Act
            val result = remoteMediator.load(LoadType.APPEND, pagingState)

            // Assert
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
            coVerify(exactly = 0) { dao.clearAll() }
            coVerify { dao.insertAll(any()) }
        }
}
