package com.tahn.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tahn.data.local.database.entity.PokemonEntity

@Dao
internal interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): PagingSource<Int, PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonEntity>)

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()
}
