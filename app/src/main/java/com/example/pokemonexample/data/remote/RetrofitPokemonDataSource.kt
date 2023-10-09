package com.example.pokemonexample.data.remote

import com.example.data.Resource
import com.example.data.source.PokemonRemoteDataSource
import com.example.domain.PokemonElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrofitPokemonDataSource @Inject constructor(
    private val pokemonApi: PokemonApi
)  : PokemonRemoteDataSource {
    override  fun getListPokemon(offset: Int): Flow<Resource<List<PokemonElement>>> = flow {
        try {
            val response = pokemonApi.getListPokemon(25,offset)
            if (response.isSuccessful ){
               emit(Resource.success(response.body()!!.results))
            }else{
               emit( Resource.error(response.message(),null))
            }
        }catch (e: Exception){
            emit(Resource.error("No es posible conectar al servidor. Revisa tu coneccion a internet",null))
        }
    }


}