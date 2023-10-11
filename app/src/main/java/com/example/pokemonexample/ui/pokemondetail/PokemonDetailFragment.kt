package com.example.pokemonexample.ui.pokemondetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.domain.PokemonDetail
import com.example.domain.PokemonDomain
import com.example.pokemonexample.R
import com.example.pokemonexample.databinding.FragmentPokemonDetailBinding
import com.example.pokemonexample.ui.pokemonlist.PokemonListViewModel
import com.example.pokemonexample.utils.Event
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private lateinit var pokemonDetailBinding: FragmentPokemonDetailBinding
    private val args: PokemonDetailFragmentArgs by navArgs()
    private val pokemonDetailViewModel:PokemonDetailViewModel by viewModels()
    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        pokemonDetailViewModel.getPokemon(args.id)
        pokemonDetailBinding = FragmentPokemonDetailBinding.inflate(inflater,container,false)
        return pokemonDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonDetailViewModel.pokemon.observe(viewLifecycleOwner, Observer(::handleEvent))
    }

    fun handleEvent(event:Event<PokemonDomain>){
        event.getContentIfNotHandled()?.let { pokemon ->
            pokemonDetailBinding.textName.text = pokemon.nombre
            pokemonDetailBinding.txtHeight.text = pokemon.altura.toString()
            pokemonDetailBinding.txtWeight.text= pokemon.peso.toString()
            glide.load(pokemon.sprites).into(pokemonDetailBinding.photo)
        }
    }

}