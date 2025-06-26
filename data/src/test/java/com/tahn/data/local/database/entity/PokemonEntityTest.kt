package com.tahn.data.local.database.entity

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonEntityTest {
    @Test
    fun `toPokemonDomain should map fields correctly`() {
        // Arrange
        val entity =
            PokemonEntity(
                id = 1,
                name = "Bulbasaur",
                imageUrl = "https://img.pokemon.com/bulbasaur.png",
            )

        // Act
        val domainModel = entity.toPokemonDomain()

        // Assert
        assertEquals(entity.id, domainModel.id)
        assertEquals(entity.name, domainModel.name)
        assertEquals(entity.imageUrl, domainModel.imageUrl)
    }
}
