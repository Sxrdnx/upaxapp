package com.example.pokemonexample.ui.pokemonlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.EventLog.Event
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Resource
import com.example.data.Status
import com.example.domain.PokemonElement
import com.example.pokemonexample.databinding.FragmentPokemonListBinding
import com.example.pokemonexample.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import com.example.pokemonexample.utils.Event as UtilsEvent

@AndroidEntryPoint
class PokemonList : Fragment() {
    private lateinit var pokemonListBinding: FragmentPokemonListBinding
    private val pokemonListViewModel: PokemonListViewModel by viewModels()
    lateinit var adapter: PokemonListAdapter
    var currentOffset = 0
    private var isRequestInProgress = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        adapter = PokemonListAdapter()
        pokemonListBinding = FragmentPokemonListBinding.inflate(inflater,container,false)
        return pokemonListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonListViewModel.getPokemonList(currentOffset, false)
        pokemonListBinding.rcyPokemonList.adapter = adapter
        scrollRecycler()


        pokemonListViewModel.pokemonList.observe(viewLifecycleOwner,Observer(::handleUi))
    }

    private fun scrollRecycler() {
        pokemonListBinding.rcyPokemonList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("SuspiciousIndentation")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if ( linearLayoutManager != null && !isRequestInProgress && linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.currentList.size - 1)) {
                    currentOffset = adapter.currentList.size
                    isRequestInProgress = true
                    pokemonListViewModel.getPokemonList(currentOffset, true)
                   // pokemonListBinding.loadingAnimationView.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun handleUi(event: UtilsEvent<Resource<List<PokemonElement>>>){
        event.getContentIfNotHandled()?.let {event
            val result = event.peekContent()
            when(result.status){
                Status.SUCCESS->{
                    pokemonListBinding.loadingAnimationView.visibility = View.GONE
                    adapter.submitList(result.data)
                }
                Status.LOADING->{
                    pokemonListBinding.loadingAnimationView.visibility = View.VISIBLE
                }

                Status.ERROR->{
                    requireActivity().toast(result.message!!)
                }
            }

        }
    }


}