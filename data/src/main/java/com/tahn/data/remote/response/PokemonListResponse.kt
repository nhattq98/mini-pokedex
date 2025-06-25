package com.tahn.data.remote.response

import com.tahn.data.local.database.entity.PokemonEntity

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>,
)

data class PokemonDto(
    val name: String,
    val url: String, // Contains ID at the end
) {
    val id: Int
        get() =
            url
                .trimEnd('/')
                .split("/")
                .last()
                .toInt()

    private val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

    fun toEntity(): PokemonEntity {
        val id =
            url
                .trimEnd('/')
                .split("/")
                .last()
                .toInt()
        return PokemonEntity(
            id = id,
            name = name,
            imageUrl = imageUrl,
        )
    }
}
