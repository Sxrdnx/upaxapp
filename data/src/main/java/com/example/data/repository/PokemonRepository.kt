package com.example.data.repository

import com.example.data.source.PokemonRemoteDataSource
import javax.inject.Inject


class PokemonRepository @Inject constructor(private val remoteDataSource: PokemonRemoteDataSource) {

    fun getListPokemon(offset: Int) = remoteDataSource.getListPokemon(offset)
}