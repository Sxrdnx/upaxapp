package com.example.pokemonexample.data.remote

import com.example.pokemonexample.data.remote.response.PokemonElementResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon/")
    suspend fun getListPokemon(@Query("limit") limit: Int, @Query("offset") offset: Int): Response<PokemonElementResponse>
}