package com.example.pokemonexample.ui.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Resource
import com.example.domain.PokemonDomain
import com.example.domain.PokemonElement
import com.example.pokemonexample.utils.Event
import com.example.usescases.GetPokemonById
import com.example.usescases.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonById
): ViewModel() {
    private val _pokemon = MutableLiveData<Event<PokemonDomain>>()
    val pokemon: LiveData<Event<PokemonDomain>> get() = _pokemon

    fun getPokemon(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _pokemon.postValue( Event(getPokemonDetailUseCase(id)!!))
        }
    }

}