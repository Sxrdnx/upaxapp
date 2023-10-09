package com.example.pokemonexample.ui.pokemonlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pokemonexample.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonList : Fragment() {
    private lateinit var pokemonListBinding: FragmentPokemonListBinding
    private val pokemonListViewModel: PokemonListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        pokemonListBinding = FragmentPokemonListBinding.inflate(inflater,container,false)
        return pokemonListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonListViewModel.exisde()

    }


}