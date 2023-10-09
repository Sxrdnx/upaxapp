package com.example.pokemonexample.di

import com.example.data.repository.PokemonRepository
import com.example.data.source.PokemonRemoteDataSource
import com.example.pokemonexample.data.remote.PokemonApi
import com.example.pokemonexample.data.remote.RetrofitPokemonDataSource
import com.example.pokemonexample.utils.Constants
import com.intuit.sdp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteApi(client: OkHttpClient): PokemonApi{
        return Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PokemonApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRemoteDatasource(pokemonApi: PokemonApi): PokemonRemoteDataSource {
        return RetrofitPokemonDataSource(pokemonApi)
    }

    @Singleton
    @Provides
    fun providePokemonRepository(pokemonRemoteDataSource: PokemonRemoteDataSource): PokemonRepository{
        return PokemonRepository(pokemonRemoteDataSource)
    }



}