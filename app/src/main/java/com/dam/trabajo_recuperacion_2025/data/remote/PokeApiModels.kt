package com.dam.trabajo_recuperacion_2025.data.remote

import com.squareup.moshi.Json

data class PokeListResponse(val results: List<PokeItem>)
data class PokeItem(val name: String, val url: String)
data class PokeDetail(val id: Int, val name: String, val sprites: Sprites)
data class Sprites(@Json(name = "front_default") val frontDefault: String?)
data class PokemonUi(val name: String, val image: String)
