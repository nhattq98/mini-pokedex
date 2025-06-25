package com.tahn.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tahn.domain.model.Pokemon

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
)

fun PokemonEntity.toPokemonDomain(): Pokemon =
    Pokemon(
        id = id,
        name = name,
        imageUrl = imageUrl,
    )
