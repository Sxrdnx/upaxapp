package com.example.pokemonexample.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_data")
    fun getPokemonByPosition(): LiveData<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_data")
    fun getPokemonOffline(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_data WHERE pokemon_id = :id")
    fun getPokemonById(id: Int): PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePokemon(pokemon: PokemonEntity)

    @Update
    fun updatePokemon(pokemon: PokemonEntity) : Int
}