package com.example.data.source

import com.example.domain.PokemonDetail
import com.example.domain.PokemonDomain
import kotlinx.coroutines.flow.Flow

interface PokemonLocalDataSource {

    fun getPokemonByPosition(): List<PokemonDomain>

    
      suspend fun getALLPokemon(): List<PokemonDomain>


    suspend  fun getPokemonById(id: Int): PokemonDomain?

    
   suspend fun saveAPokemon(pokemon: PokemonDetail)

    
    fun updatePokemon(pokemon: PokemonDomain) : Int
}