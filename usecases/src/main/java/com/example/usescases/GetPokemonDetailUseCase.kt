package com.example.usescases

import com.example.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor (private val repository: PokemonRepository){
    operator fun invoke(url:String) = repository.getPokemonDetail(url)
}