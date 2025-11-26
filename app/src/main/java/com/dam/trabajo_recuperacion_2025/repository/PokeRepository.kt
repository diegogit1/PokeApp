package com.dam.trabajo_recuperacion_2025.repository

import com.dam.trabajo_recuperacion_2025.data.remote.PokeApiService
import com.dam.trabajo_recuperacion_2025.data.remote.PokemonUi

class PokeRepository(private val api: PokeApiService) {
    suspend fun fetchPokemonList(limit: Int = 30): List<PokemonUi> {
        val out = mutableListOf<PokemonUi>()
        val res = api.list(limit)
        for (item in res.results) {
            val id = extractIdFromUrl(item.url)
            val img = if (id != null) "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png" else ""
            out.add(PokemonUi(item.name.replaceFirstChar { it.uppercase() }, img))
        }
        return out
    }

    private fun extractIdFromUrl(url: String): Int? {
        return try {
            val parts = url.trimEnd('/').split('/')
            parts.last().toIntOrNull()
        } catch (e: Exception) {
            null
        }
    }
}

