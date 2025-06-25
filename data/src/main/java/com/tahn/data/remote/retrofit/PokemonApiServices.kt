package com.tahn.data.remote.retrofit

import com.tahn.data.remote.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface PokemonApiServices {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): PokemonListResponse
}
