package com.tahn.data.local.database.dao

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tahn.data.local.database.AppDatabase
import com.tahn.data.local.database.entity.PokemonEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
internal class PokemonDaoTest {
    private lateinit var roomDatabase: AppDatabase
    private lateinit var pokemonDao: PokemonDao

    @Before
    fun setup() {
        roomDatabase =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext<Context>(),
                    AppDatabase::class.java,
                ).build()
        pokemonDao = roomDatabase.pokemonDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        roomDatabase.close()
    }

    @Test
    fun insertAll_and_getAllPokemon_returnsCorrectData() =
        runTest {
            val sampleList =
                listOf(
                    PokemonEntity(id = 1, name = "Bulbasaur", imageUrl = ""),
                    PokemonEntity(id = 2, name = "Charmander", imageUrl = ""),
                )

            pokemonDao.insertAll(sampleList)

            val pagingSource = pokemonDao.getAllPokemon()
            val loadResult =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                )

            val data = (loadResult as PagingSource.LoadResult.Page).data
            assertEquals(2, data.size)
            assertEquals("Bulbasaur", data[0].name)
            assertEquals("Charmander", data[1].name)
        }

    @Test
    fun clearAll_removesAllData() =
        runTest {
            pokemonDao.insertAll(
                listOf(PokemonEntity(id = 1, name = "Squirtle", imageUrl = "")),
            )
            pokemonDao.clearAll()

            val pagingSource = pokemonDao.getAllPokemon()
            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                )

            val data = (result as PagingSource.LoadResult.Page).data
            assertTrue(data.isEmpty())
        }
}
