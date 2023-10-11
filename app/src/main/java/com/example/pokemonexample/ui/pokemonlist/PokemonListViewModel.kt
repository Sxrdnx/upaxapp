package com.example.pokemonexample.ui.pokemonlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Resource
import com.example.data.Status
import com.example.data.repository.PokemonRepository
import com.example.domain.PokemonElement
import com.example.pokemonexample.utils.Event
import com.example.usescases.GetPokemonDetailUseCase
import com.example.usescases.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    var totalLoaded = 0
    private val _loading = MutableLiveData<Event<Boolean>>()
    val loading: LiveData<Event<Boolean>> get() = _loading


    private val _pokemonLits = MutableLiveData<Event<Resource<List<PokemonElement>>>>()
    val pokemonList : LiveData<Event<Resource<List<PokemonElement>>>> get() = _pokemonLits

    fun getPokemonDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = pokemonRepository.getAllPokemonsLocal().map {  PokemonElement(it.nombre,it.sprites,it.favorite, id = it.id)}
            _pokemonLits.postValue(Event(Resource.success(result)))
        }
    }

    fun getPokemonList(offset : Int, load: Boolean){
        viewModelScope.launch {
            _pokemonLits.postValue(Event(Resource.loading(null)))
            getPokemonListUseCase(offset).collect { result ->
                if (result.status == Status.SUCCESS) {
                    val totalRequests = result.data!!.size
                    var succResponse = 0
                    result.data!!.forEach { p ->
                        getPokemonDetailUseCase(p.url).collect() { detailResource ->
                            if (detailResource.status == Status.SUCCESS) {
                                succResponse++
                                if (succResponse ==totalRequests) {
                                    totalLoaded += totalRequests
                                }
                            }
                        }
                    }
                    getPokemonDatabase()
                } else {
                    _pokemonLits.postValue(Event(Resource.error("No tienes coneccion a internet ", null)))
                    getPokemonDatabase()
                }
            }
        }
    }
}