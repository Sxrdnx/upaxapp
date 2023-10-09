package com.example.data.source

import com.example.data.Resource
import com.example.domain.PokemonElement
import kotlinx.coroutines.flow.Flow


interface PokemonRemoteDataSource {
     fun getListPokemon(offset: Int): Flow<Resource<List<PokemonElement>>>
}