package com.example.usescases

import com.example.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(offset : Int) = pokemonRepository.getListPokemon(offset)
}