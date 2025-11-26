package com.dam.trabajo_recuperacion_2025.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dam.trabajo_recuperacion_2025.viewmodel.PokeViewModel
import com.dam.trabajo_recuperacion_2025.ui.components.Loading

@Composable
fun PokeListScreen(navController: NavController, pokeVm: PokeViewModel) {
    var count by remember { mutableStateOf(30f) }
    val list by pokeVm.pokemonList.collectAsState()
    val loading by pokeVm.loading.collectAsState()
    val message by pokeVm.message.collectAsState()
    LaunchedEffect(Unit) { pokeVm.loadPokemon(count.toInt()) }

    Column(Modifier.fillMaxSize().padding(8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Mostrar: ${count.toInt()}")
            Button(onClick = { pokeVm.loadPokemon(count.toInt()) }) { Text("Cargar") }
        }
        Spacer(Modifier.height(8.dp))
        Slider(
            value = count,
            onValueChange = { count = it },
            valueRange = 1f..151f,
            steps = 150,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        if (loading) {
            Box(Modifier.fillMaxWidth().height(120.dp)) { Loading() }
        }
        if (message.isNotEmpty() && list.isEmpty()) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text(text = message)
                Spacer(Modifier.height(8.dp))
                Button(onClick = { pokeVm.loadPokemon(count.toInt()) }) { Text("Reintentar") }
            }
        }
        if (list.isNotEmpty()) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(list) { p ->
                    Card(Modifier.fillMaxWidth().padding(8.dp).clickable { }) {
                        Row(Modifier.padding(8.dp)) {
                            AsyncImage(model = p.image, contentDescription = p.name, modifier = Modifier.size(64.dp))
                            Spacer(Modifier.width(8.dp))
                            Column { Text(p.name) }
                        }
                    }
                }
            }
        }
    }
}


