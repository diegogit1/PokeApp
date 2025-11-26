package com.dam.trabajo_recuperacion_2025.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dam.trabajo_recuperacion_2025.data.remote.PokeApiService
import com.dam.trabajo_recuperacion_2025.data.remote.PokemonUi
import com.dam.trabajo_recuperacion_2025.repository.PokeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class PokeViewModel : ViewModel() {
    private val repo = PokeRepository(PokeApiService.create())
    private val _pokemonList = MutableStateFlow<List<PokemonUi>>(emptyList())
    val pokemonList = _pokemonList.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun loadPokemon(limit: Int = 30) {
        viewModelScope.launch {
            _loading.value = true
            _message.value = ""
            try {
                val list = repo.fetchPokemonList(limit)
                if (list.isEmpty()) {
                    _message.value = "No hay datos disponibles"
                }
                _pokemonList.value = list
            } catch (e: Exception) {
                Log.e("PokeViewModel", "loadPokemon error", e)
                _message.value = e.localizedMessage ?: "Error al cargar datos"
            } finally {
                _loading.value = false
            }
        }
    }
}

