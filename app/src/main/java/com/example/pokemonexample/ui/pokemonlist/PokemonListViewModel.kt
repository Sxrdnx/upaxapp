package com.example.pokemonexample.ui.pokemonlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Resource
import com.example.data.Status
import com.example.data.repository.PokemonRepository
import com.example.domain.PokemonElement
import com.example.usescases.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
): ViewModel() {


   fun exisde(){
        viewModelScope.launch {
            getPokemonListUseCase(0).collect(){result ->
                if(result.status == Status.SUCCESS){
                    Log.d("DATAP", result.data.toString())
                }
            }

        }
    }
}