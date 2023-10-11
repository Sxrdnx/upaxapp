package com.example.pokemonexample.ui.pokemonlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.PokemonElement
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.pokemonexample.R

import com.example.pokemonexample.databinding.ItemPokemonBinding

class PokemonListAdapter(
    private val glide: RequestManager,
    private val listener: PokemonListener,
    ): ListAdapter<PokemonElement,PokemonListAdapter.ViewHolder>(DiffUtilCallBack) {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var context: Context


   inner class ViewHolder(private val pokemonlistview: ItemPokemonBinding): RecyclerView.ViewHolder(pokemonlistview.root){
        fun bid(pokemonElement: PokemonElement)=with(pokemonlistview){
            root.setOnClickListener {
                listener.onClickElement(pokemonElement.id)
            }
            if (pokemonElement.url.isNotEmpty()){
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img)
                glide.load(pokemonElement.url).apply(requestOptions).into(imageEmploye)

                textName.text = pokemonElement.name
            }

        }

    }

    private object DiffUtilCallBack: DiffUtil.ItemCallback<PokemonElement>(){
        override fun areItemsTheSame(oldItem: PokemonElement, newItem: PokemonElement): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: PokemonElement, newItem: PokemonElement): Boolean = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemPokemonBinding = ItemPokemonBinding.inflate(
            layoutInflater,
            parent,false
        )
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bid(pokemon)
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }
}