package com.example.usescases

import com.example.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonById @Inject constructor(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(id: Int)=
        pokemonRepository.getPokemonById(id)

}